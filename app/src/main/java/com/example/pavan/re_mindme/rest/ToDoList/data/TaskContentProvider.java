package com.example.pavan.re_mindme.rest.ToDoList.data;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import static com.example.pavan.re_mindme.rest.ToDoList.data.TaskContract.TaskEntry.TABLE_NAME;


    // COMPLETED: Verify that TaskContentProvider extends from ContentProvider and implements required methods
    public class TaskContentProvider extends ContentProvider {

        // COMPLETED: Define final integer constants for the directory of tasks and a single item.
        // It's convention to use 100, 200, 300, etc for directories,
        // and related ints (101, 102, ..) for items in that directory.
        public static final int TASKS = 100;
        public static final int TASK_WITH_ID = 101;

        // COMPLETED: Declare a static variable for the Uri matcher that you construct
        private static final UriMatcher sUriMatcher = buildUriMatcher();

        // COMPLETED: Define a static buildUriMatcher method that associates URI's with their int match
        /**
         Initialize a new matcher object without any matches,
         then use .addURI(String authority, String path, int match) to add matches
         */
        public static UriMatcher buildUriMatcher() {

            // Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
            UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        /*
          All paths added to the UriMatcher have a corresponding int.
          For each kind of uri you may want to access, add the corresponding match with addURI.
          The two calls below add matches for the task directory and a single item by ID.
         */
            uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS, TASKS);
            uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS + "/#", TASK_WITH_ID);

            return uriMatcher;
        }


        // Member variable for a TaskDbHelper that's initialized in the onCreate() method
        private TaskDbHelper mTaskDbHelper;


        /* onCreate() is where you should initialize anything you’ll need to setup
        your underlying data source.
        In this case, you’re working with a SQLite database, so you’ll need to
        initialize a DbHelper to gain access to it.
         */
        @Override
        public boolean onCreate() {
            // COMPLETED: Complete onCreate() and initialize a TaskDbhelper on startup
            // [Hint] Declare the DbHelper as a global variable

            Context context = getContext();
            mTaskDbHelper = new TaskDbHelper(context);
            return true;
        }


        // COMPLETED: Implement insert to handle requests to insert a single new row of data
        @Override
        public Uri insert(@NonNull Uri uri, ContentValues values) {

            // COMPLETED: 1. Get access to the task database (to write new data to)
            final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();

            // COMPLETED: 2. Write URI matching code to identify the match for the tasks directory
            int match = sUriMatcher.match(uri);

            // COMPLETED: 3. Insert new values into the database
            // COMPLETED: 4. Set the value for the returnedUri and write the default case for unknown URI's

            Uri returnUri; // to be returned

            switch (match) {
                case TASKS:
                    // Inserting values into tasks table
                    long id = db.insert(TABLE_NAME, null, values);
                    if ( id > 0 ) {
                        returnUri = ContentUris.withAppendedId(TaskContract.TaskEntry.CONTENT_URI, id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                    break;
                // Default case throws an UnsupportedOperationException
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }

            // COMPLETED: 5. Notify the resolver if the uri has been changed
            getContext().getContentResolver().notifyChange(uri, null);

            // Return constructed uri (this points to the newly inserted row of data)
            return returnUri;
        }


        // COMPLETED: Implement query to handle requests for data by URI
        @Override
        public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                            String[] selectionArgs, String sortOrder) {

            // COMPLETED: 1. Get access to underlying database (read-only for query)
            final SQLiteDatabase db = mTaskDbHelper.getReadableDatabase();

            // COMPLETED: 2. Write URI match code
            int match = sUriMatcher.match(uri);

            // COMPLETED: 3. Query for the tasks directory and write a default case

            Cursor retCursor;

            switch (match) {
                // Query for the tasks directory
                case TASKS:
                    retCursor =  db.query(TABLE_NAME,
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                    break;
                // Default exception
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }

            // COMPLETED: 4. Set a notification URI on the Cursor
            retCursor.setNotificationUri(getContext().getContentResolver(), uri);

            // Return the desired Cursor
            return retCursor;
        }


        // COMPLETED: Implement delete to delete a single row of data
        @Override
        public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

            // COMPLETED: 1. Get access to the database and write URI matching code to recognize a single item
            final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();

            int match = sUriMatcher.match(uri);

            // COMPLETED: 2. Write the code to delete a single row of data
            //[Hint] Use selections to delete an item by its row ID

            // Keep track of the number of deleted tasks
            int tasksDeleted; // init as 0

            switch (match) {
                // Handle the single item case, recognized by the ID included in the URI path
                case TASK_WITH_ID:
                    // Get the task ID from the URI path
                    String id = uri.getPathSegments().get(1);
                    // Use selections/selectionArgs to filter for this ID
                    tasksDeleted = db.delete(TABLE_NAME, "_id=?", new String[]{id});
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }

            // COMPLETED: 3. Notify the resolver of a change
            // And don't forget to return the number of items deleted!

            if (tasksDeleted != 0) {
                // A task was deleted, set notification
                getContext().getContentResolver().notifyChange(uri, null);
            }

            // Return the number of tasks deleted
            return tasksDeleted;
        }


        // Update won't be used in the final ToDoList app but is implemented here for completeness
        // This updates a single item (by it's ID) in the tasks directory
        @Override
        public int update(@NonNull Uri uri, ContentValues values, String selection,
                          String[] selectionArgs) {

            // Access the database
            final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();

            // match code
            int match = sUriMatcher.match(uri);

            //Keep track of if an update occurs
            int tasksUpdated;

            switch (match) {
                case TASK_WITH_ID:
                    //update a single task by getting the id
                    String id = uri.getPathSegments().get(1);
                    //using selections
                    tasksUpdated = db.update(TABLE_NAME, values, "_id=?", new String[]{id});
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }

            if (tasksUpdated != 0) {
                //set notifications if a task was updated
                getContext().getContentResolver().notifyChange(uri, null);
            }

            // return number of tasks updated
            return tasksUpdated;
        }



        public String getType(@NonNull Uri uri) {

            throw new UnsupportedOperationException("Not yet implemented");
        }
}

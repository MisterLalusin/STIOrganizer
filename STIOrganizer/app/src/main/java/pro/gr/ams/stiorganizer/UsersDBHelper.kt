package pro.gr.ams.stiorganizer

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

class UsersDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val TABLE_NAME = "users"
    val COL_1 = "userid"
    val COL_2 = "name"
    val COL_3 = "password"
    val COL_4 = "campus"
    val COL_5 = "program"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)

        staticData(db)
    }

    private fun staticData(db: SQLiteDatabase) {

        val staticUser01 = ContentValues()
        staticUser01.put(COL_1, "2000080654")
        staticUser01.put(COL_2, "John Rovic Lalusin")
        staticUser01.put(COL_3, "01234567")
        staticUser01.put(COL_4, "STI Lipa")
        staticUser01.put(COL_5, "BSCpE")
        db.insert(TABLE_NAME, null, staticUser01)

        val staticUser02 = ContentValues()
        staticUser02.put(COL_1, "2000104563")
        staticUser02.put(COL_2, "Mae Paulene Ilagan")
        staticUser02.put(COL_3, "12345678")
        staticUser02.put(COL_4, "STI Lipa")
        staticUser02.put(COL_5, "BSCpE")
        db.insert(TABLE_NAME, null, staticUser02)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        db.execSQL(udata_SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertUser(user: AccountsModel): Boolean {
        val db = writableDatabase

        val values = ContentValues()
        values.put(COL_1, user.userid)
        values.put(COL_2, user.name)
        values.put(COL_3, user.password)
        values.put(COL_4, user.campus)
        values.put(COL_5, user.program)

        val newRowId = db.insert(TABLE_NAME, null, values)

        return true
    }


    @Throws(SQLiteConstraintException::class)
    fun insertUdata(udata: UdataInsertModel): Boolean {
        val db = writableDatabase

        val values = ContentValues()
        values.put(udata_COL_2, udata.title)
        values.put(udata_COL_3, udata.content)
        values.put(udata_COL_4, udata.user)

        val newRowId = db.insert(udata_TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteUser(userid: String): Boolean {
        val db = writableDatabase
        val selection = COL_1 + " LIKE ?"
        val selectionArgs = arrayOf(userid)
        db.delete(TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readUser(userid: String): ArrayList<AccountsModel> {
        val users = ArrayList<AccountsModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + TABLE_NAME + " WHERE " + COL_1 + "='" + userid + "'", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var name: String
        var pass: String
        var campus: String
        var program: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                name = cursor.getString(cursor.getColumnIndex(COL_2))
                pass = cursor.getString(cursor.getColumnIndex(COL_3))
                campus = cursor.getString(cursor.getColumnIndex(COL_4))
                program = cursor.getString(cursor.getColumnIndex(COL_5))

                users.add(AccountsModel(userid, name, pass, campus, program))
                cursor.moveToNext()
            }
        }
        return users
    }

    fun readAllUsers(): ArrayList<AccountsModel> {
        val users = ArrayList<AccountsModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var userid: String
        var name: String
        var pass: String
        var campus: String
        var program: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                userid = cursor.getString(cursor.getColumnIndex(COL_1))
                name = cursor.getString(cursor.getColumnIndex(COL_2))
                pass = cursor.getString(cursor.getColumnIndex(COL_3))
                campus = cursor.getString(cursor.getColumnIndex(COL_4))
                program = cursor.getString(cursor.getColumnIndex(COL_5))

                users.add(AccountsModel(userid, name, pass, campus, program))
                cursor.moveToNext()
            }
        }
        return users
    }

    fun readAllUData(): ArrayList<UdataModel> {
        val userData = ArrayList<UdataModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + udata_TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(udata_SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var dataid: String
        var title: String
        var content: String
        var user: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                dataid = cursor.getString(cursor.getColumnIndex(udata_COL_1))
                title = cursor.getString(cursor.getColumnIndex(udata_COL_2))
                content = cursor.getString(cursor.getColumnIndex(udata_COL_3))
                user = cursor.getString(cursor.getColumnIndex(udata_COL_4))

                userData.add(UdataModel(dataid, title, content, user))
                cursor.moveToNext()
            }
        }
        return userData
    }

    fun authenticateUser(userid: String, password: String): ArrayList<AccountsModel> {
        val users = ArrayList<AccountsModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + TABLE_NAME + " WHERE " + COL_1 + "='" + userid + "' and " + COL_3 + "='" + password + "'", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var name: String
        var pass: String
        var campus: String
        var program: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                name = cursor.getString(cursor.getColumnIndex(COL_2))
                pass = cursor.getString(cursor.getColumnIndex(COL_3))
                campus = cursor.getString(cursor.getColumnIndex(COL_4))
                program = cursor.getString(cursor.getColumnIndex(COL_5))

                users.add(AccountsModel(userid, name, pass, campus, program))
                cursor.moveToNext()
            }
        }
        return users
    }
    fun checkUserExist(userid: String): ArrayList<AccountsModel> {
        val users = ArrayList<AccountsModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + TABLE_NAME + " WHERE " + COL_1 + "='" + userid + "'", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var name: String
        var pass: String
        var campus: String
        var program: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                name = cursor.getString(cursor.getColumnIndex(COL_2))
                pass = cursor.getString(cursor.getColumnIndex(COL_3))
                campus = cursor.getString(cursor.getColumnIndex(COL_4))
                program = cursor.getString(cursor.getColumnIndex(COL_5))

                users.add(AccountsModel(userid, name, pass, campus, program))
                cursor.moveToNext()
            }
        }
        return users
    }

    fun updatePassword(userid: String, password: String): ArrayList<AccountsModel> {
        val users = ArrayList<AccountsModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("update " + TABLE_NAME + " SET " + COL_3 + "=" + password + " WHERE " + COL_1 + "='" + userid + "'", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var name: String
        var pass: String
        var campus: String
        var program: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                name = cursor.getString(cursor.getColumnIndex(COL_2))
                pass = cursor.getString(cursor.getColumnIndex(COL_3))
                campus = cursor.getString(cursor.getColumnIndex(COL_4))
                program = cursor.getString(cursor.getColumnIndex(COL_5))

                users.add(AccountsModel(userid, name, pass, campus, program))
                cursor.moveToNext()
            }
        }
        return users
    }

    fun updateNotes(dataid: String, title: String, content: String): ArrayList<UpdateNotesModel> {
        val notes = ArrayList<UpdateNotesModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("update " + udata_TABLE_NAME + " SET " + udata_COL_2 + "='" + title + "', " + udata_COL_3 + " ='" + content + "' WHERE " + udata_COL_1 + "='" + dataid + "'", null)
        } catch (e: SQLiteException) {
            db.execSQL(udata_SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var dataid: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                dataid = cursor.getString(cursor.getColumnIndex(COL_1))

                notes.add(UpdateNotesModel(dataid, title, content))
                cursor.moveToNext()
            }
        }
        return notes
    }

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "accounts.db"

        val TABLE_NAME = "users"
        val COL_1 = "userid"
        val COL_2 = "name"
        val COL_3 = "password"
        val COL_4 = "campus"
        val COL_5 = "program"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_1 + " TEXT PRIMARY KEY," +
                    COL_2 + " TEXT," +
                    COL_3 + " TEXT," +
                    COL_4 + " TEXT," +
                    COL_5 + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME

        val udata_TABLE_NAME = "userdata"
        val udata_COL_1 = "dataid"
        val udata_COL_2 = "title"
        val udata_COL_3 = "content"
        val udata_COL_4 = "user"

        private val udata_SQL_CREATE_ENTRIES =
            "CREATE TABLE " + udata_TABLE_NAME + " (" +
                    udata_COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    udata_COL_2 + " TEXT," +
                    udata_COL_3 + " TEXT," +
                    udata_COL_4 + " TEXT)"

        private val udata_SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + udata_TABLE_NAME
    }


    class AccountsModel(val userid: String, val name: String, val password: String, val campus: String, val program: String)


    class UdataModel(val dataid: String, val title: String, val content: String, val user: String)
    class UdataInsertModel(val title: String, val content: String, val user: String)
    class UpdateNotesModel(val dataid: String, val title: String, val content: String)

}

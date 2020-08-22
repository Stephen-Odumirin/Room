package ps.stdev.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Book::class],version = 3)//how to migrate database
@TypeConverters(DateTypeConverter::class)
abstract class BookRoomDatabase : RoomDatabase(){

    abstract fun bookDao() : BookDao

    companion object {
        private var bookRoomInstance : BookRoomDatabase? = null

        //migration shii
        private val MIGRATION_1_2 : Migration = object : Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE books_table_name ADD COLUMN _description TEXT DEFAULT 'Add Description' NOT NULL")
            }

        }

        private val MIGRATION_2_3 : Migration = object : Migration(2,3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE books_table_name ADD COLUMN _lastUpdated INTEGER DEFAULT NULL")
            }

        }

        fun getDatabase(context: Context) : BookRoomDatabase?{
            if (bookRoomInstance == null){
                synchronized(BookRoomDatabase::class.java){
                    if (bookRoomInstance == null){
                        bookRoomInstance = Room.databaseBuilder<BookRoomDatabase>(context.applicationContext,
                        BookRoomDatabase::class.java,"book_database")
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)//i can add multiple migrations to the shii
                            .build()
                    }
                }
            }
            return bookRoomInstance
        }
    }
}
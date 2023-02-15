package pt.ipp.estg.cmu.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ChargerEntity::class, ChargerUser::class],
    version = 7,
    exportSchema = false
)
abstract class ChargerRoomDB : RoomDatabase() {
    abstract fun chargerDAO(): ChargerDAO
    abstract fun chargerUserDAO(): ChargerUserDAO

    companion object {
        @Volatile
        private var INSTANCE: ChargerRoomDB? = null

        fun getInstance(context: Context): ChargerRoomDB {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ChargerRoomDB::class.java,
                        "powerless_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}
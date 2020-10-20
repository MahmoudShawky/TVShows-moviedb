package eg.mahmoudshawky.theAir.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import eg.mahmoudshawky.theAir.data.local.db.converter.IntegerListConverter
import eg.mahmoudshawky.theAir.data.local.db.converter.StringListConverter
import eg.mahmoudshawky.theAir.data.models.entity.TvShow

/***
 * @author mahmoud.shawky
 * Application Room Database
 */
@Database(
    entities = [TvShow::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [(StringListConverter::class), (IntegerListConverter::class)])
abstract class TheAirDatabase : RoomDatabase(){

}
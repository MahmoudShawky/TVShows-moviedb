package eg.mahmoudshawky.theAir.data

import eg.mahmoudshawky.theAir.data.local.db.DatabaseHelper
import eg.mahmoudshawky.theAir.data.local.prefs.PreferencesHelper
import eg.mahmoudshawky.theAir.data.remote.RemoteRepository

/***
 * @author mahmoud.shawky
 *
 * Repository is an interface the should be implemented to access the application data layer
 */
interface Repository : DatabaseHelper, PreferencesHelper, RemoteRepository



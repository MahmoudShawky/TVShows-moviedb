package eg.mahmoudshawky.theAir.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eg.mahmoudshawky.theAir.R
import eg.mahmoudshawky.theAir.ui.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
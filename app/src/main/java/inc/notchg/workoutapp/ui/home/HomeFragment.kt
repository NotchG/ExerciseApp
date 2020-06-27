package inc.notchg.workoutapp.ui.home

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import inc.notchg.workoutapp.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var sensorM: SensorManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sensorM =  activity!!.getSystemService(SENSOR_SERVICE) as SensorManager
        val proximityS: Sensor = sensorM.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        if (proximityS == null) {
            Toast.makeText(context, "Proximity sensor is unavalaible", Toast.LENGTH_SHORT).show()
        }

        val proximitySensorL: SensorEventListener = object: SensorEventListener {
            override fun onSensorChanged(p0: SensorEvent?) {
                Toast.makeText(context, p0!!.values[0].toInt(), Toast.LENGTH_SHORT).show()
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

            }
        }

        sensorM.registerListener(proximitySensorL, proximityS, 2 * 1000 * 1000)
    }

    override fun onPause() {
        super.onPause()
    }
}
package au.edu.swin.sdmd.w03_calculations

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    var operatorResult: Int = 0
    var operator = "plus"

    //lifecycles for the activity
    override fun onStart() {
        super.onStart()
        Log.i("LIFECYCLE", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("LIFECYCLE", "onResume")
    }

    override fun onPause(){
        super.onPause()
        Log.i("LIFECYCLE", "onPause")
        val sharedPref = this.getSharedPreferences("testCalcFile",Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()){
            val number1 = findViewById<TextView>(R.id.number1)
            putString("num1", number1.text.toString())
            val number2 = findViewById<TextView>(R.id.number2)
            putString("num2", number2.text.toString())

            apply()
        }

    }

    override fun onStop(){
        super.onStop()
        Log.i("LIFECYCLE", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("LIFECYCLE", "onDestroy")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("LIFECYCLE", "onCreate")

        val number1 = findViewById<EditText>(R.id.number1)
        val number2 = findViewById<EditText>(R.id.number2)
        val answer = findViewById<TextView>(R.id.answer)

        val sharedPref = this.getSharedPreferences("testCalcFile",Context.MODE_PRIVATE) ?: return
        val num1 = sharedPref.getString("num1", "0").toString()
        val num2 = sharedPref.getString("num2", "0").toString()
        operator = sharedPref.getString("operator", "plus").toString()

        number1.setText(num1)
        number2.setText(num2)

        operatorResult = when(operator){
            "plus" -> add(number1.text.toString(), number2.text.toString())
            "mult" -> mult(number1.text.toString(), number2.text.toString())
            else -> add(number1.text.toString(), number2.text.toString())
        }
        answer.setText(operatorResult.toString())

        //saveInstanceState, used for the result to be kept on the screen
        //savedInstanceState?.let {
            //operatorResult = savedInstanceState.getInt("answer")
           // answer.setText(operatorResult.toString())
        //}

        val equals = findViewById<Button>(R.id.equals)
        equals.setOnClickListener {
            val result = when(operator) {
                "plus" -> add(number1.text.toString(), number2.text.toString())
                "mult" -> mult(number1.text.toString(), number2.text.toString())
                else -> add(number1.text.toString(), number2.text.toString())
            }

            // TODO: show result on the screen
            answer.setText(result.toString())

        }
    }

    // adds two numbers together
    private fun add(number1: String, number2: String) = number1.toInt() + number2.toInt()
    private fun mult(number1: String, number2: String) = number1.toInt() * number2.toInt()

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radioPlus ->
                    if (checked) {
                        operator ="plus"
                    }
                R.id.radioMult ->
                    if (checked) {
                        operator = "mult"
                    }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("answer", operatorResult)
        Log.i("LIFECYCLE", "$operatorResult")


    }

}

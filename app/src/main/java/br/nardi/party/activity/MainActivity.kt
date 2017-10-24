package br.nardi.party.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import br.nardi.party.R
import br.nardi.party.constants.EndOfYearConstants
import br.nardi.party.util.SecurityPreferences
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val viewHolder: ViewHolder = ViewHolder()
    private lateinit var securityPreferences: SecurityPreferences
    private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

    override fun onClick(v: View?) {
        val id = v?.id
        if (id == R.id.button_confirm) {
            val presence = securityPreferences.getString(EndOfYearConstants.PRESENCE)
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(EndOfYearConstants.PRESENCE, presence)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.mipmap.ic_launcher)

        this.viewHolder.today = findViewById(R.id.text_today) as TextView
        this.viewHolder.daysLeft = findViewById(R.id.text_days_left) as TextView
        this.viewHolder.confirm = findViewById(R.id.button_confirm) as Button
        this.viewHolder.confirm.setOnClickListener(this)

        this.securityPreferences = SecurityPreferences(this)

        // today
        this.viewHolder.today.text = this.simpleDateFormat.format(Calendar.getInstance().time)

        // remaining days
        this.viewHolder.daysLeft.text = "${getDaysLeftEndOfYear()} ${getString(R.string.days)}"
    }

    override fun onResume() {
        super.onResume()
        this.verifyPresence()
    }

    private fun getDaysLeftEndOfYear(): String {
        val calendar = Calendar.getInstance()
        val today = calendar.get(Calendar.DAY_OF_YEAR)

        val calendarEndOfYear = Calendar.getInstance()
        val december31 = calendarEndOfYear.getActualMaximum(Calendar.DAY_OF_YEAR)
        return (december31 - today).toString()
    }

    private fun verifyPresence() {
        val presence = securityPreferences.getString(EndOfYearConstants.PRESENCE)
        when (presence) {
            "" -> this.viewHolder.confirm.setText(R.string.not_confirmed)
            EndOfYearConstants.CONFIRM_WILL_GO -> this.viewHolder.confirm.setText(R.string.yes)
            else -> this.viewHolder.confirm.setText(R.string.not)
        }
    }

    private class ViewHolder {
        lateinit var today: TextView
        lateinit var daysLeft: TextView
        lateinit var confirm: Button
    }

}

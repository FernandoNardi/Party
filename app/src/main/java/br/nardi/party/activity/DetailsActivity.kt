package br.nardi.party.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import br.nardi.party.R
import br.nardi.party.constants.EndOfYearConstants
import br.nardi.party.util.SecurityPreferences

class DetailsActivity : AppCompatActivity(), View.OnClickListener {

    private val viewHolder = ViewHolder()
    private lateinit var securityPreferences: SecurityPreferences

    override fun onClick(v: View?) {
        val id = v?.id
        if (id == R.id.check_participate) {
            if (this.viewHolder.confirm.isChecked) {
                securityPreferences.storeString(EndOfYearConstants.PRESENCE, EndOfYearConstants.CONFIRM_WILL_GO)
            } else {
                securityPreferences.storeString(EndOfYearConstants.PRESENCE, EndOfYearConstants.CONFIRM_WONT_GO)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.mipmap.ic_launcher)

        this.viewHolder.confirm = findViewById(R.id.check_participate) as CheckBox
        this.viewHolder.confirm.setOnClickListener(this)

        this.securityPreferences = SecurityPreferences(this)
        this.loadDataFromActivity()
    }

    private fun loadDataFromActivity() {
        val extras = intent.extras
        if (extras != null) {
            val presence = securityPreferences.getString(EndOfYearConstants.PRESENCE)
            this.viewHolder.confirm.isChecked = (presence == EndOfYearConstants.CONFIRM_WILL_GO)
        }
    }

    private class ViewHolder {
        lateinit var confirm: CheckBox
    }
}

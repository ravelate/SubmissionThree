package com.fianku.submissionthree.ui.fav

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fianku.submissionthree.Adapter.FavoriteAdapter
import com.fianku.submissionthree.ui.FavViewModelFactory
import com.fianku.submissionthree.R
import com.fianku.submissionthree.database.Favorite
import com.fianku.submissionthree.databinding.ActivityFavoriteBinding
import com.fianku.submissionthree.ui.detail.DetailActivity


class FavoriteActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        _activityMainBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val adapter = FavoriteAdapter()
        val favViewModel = obtainViewModel(this@FavoriteActivity)
        favViewModel.getAllFav().observe(this, { noteList ->
            if (noteList != null) {
                adapter.setListNotes(noteList)
            }
        })
        binding?.rvNotes?.layoutManager = LinearLayoutManager(this)
        binding?.rvNotes?.setHasFixedSize(true)
        binding?.rvNotes?.adapter = adapter
        adapter.setUnfollowItemClickCallback(object : FavoriteAdapter.OnUnfollowClickCallback {
            override fun onUnfollowClicked(data: Favorite) {
                showAlertDialog(ALERT_DIALOG_DELETE, data)
            }
        })
        adapter.setOnItemsClickCallback(object : FavoriteAdapter.OnItemsClickCallback {
            override fun onItemsClicked(data: Favorite) {
                showSelectedHero(data.login.toString())
            }
        })
    }
    private fun showSelectedHero(hero: String) {
        val moveIntent = Intent(this@FavoriteActivity, DetailActivity::class.java)
        moveIntent.putExtra(DetailActivity.USER_DATA, hero)
        startActivity(moveIntent)
    }
    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }
    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }
        private fun showAlertDialog(type: Int,note: Favorite) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    val favViewModel = obtainViewModel(this@FavoriteActivity)
                    favViewModel.delete(note)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    companion object {
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
}
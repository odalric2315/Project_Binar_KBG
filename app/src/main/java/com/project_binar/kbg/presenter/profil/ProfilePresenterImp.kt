package com.project_binar.kbg.presenter.profil

import com.project_binar.kbg.data.db.PlayerDao
import com.project_binar.kbg.ui.profile.ProfileView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfilePresenterImp(private val view: ProfileView, private val playerDao: PlayerDao) :
    ProfilePresenter {
    override fun updateNamePlayer(nama: String, id: Int) {
        GlobalScope.launch {
            playerDao.updateNamePlayer(id, nama)
            view.showUpdatePlayer()
        }
    }
}
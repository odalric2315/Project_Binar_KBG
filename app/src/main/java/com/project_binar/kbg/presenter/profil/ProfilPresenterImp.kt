package com.project_binar.kbg.presenter.profil

import com.project_binar.kbg.data.db.PlayerDao
import com.project_binar.kbg.ui.profile.ProfilView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfilPresenterImp(private val view: ProfilView, private val playerDao: PlayerDao):
    ProfilPresenter {
    override fun updateNamePlayer(nama: String, id: Int) {
        GlobalScope.launch {
            playerDao.updateNamePlayer(id, nama)
            view.showUpdatePlayer()
        }
    }


}
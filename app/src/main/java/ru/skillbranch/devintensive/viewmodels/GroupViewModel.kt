package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.*
import ru.skillbranch.devintensive.extensions.mutableLivaData
import ru.skillbranch.devintensive.models.data.UserItem
import ru.skillbranch.devintensive.repositories.GroupRepository

class GroupViewModel() : ViewModel() {

    private var groupRepository = GroupRepository
    private var userItems = mutableLivaData(loadUsers())
    private var selectedItems = Transformations.map(userItems) {users -> users.filter { it.isSelected }}


    private val query = MutableLiveData("")

    fun getUserData(): LiveData<List<UserItem>> {
        val mediatorResult = MediatorLiveData<List<UserItem>>()
        val filterF = {
            val queryStr = query.value!!
            val users: List<UserItem> = userItems.value!!

            mediatorResult.value =
                if (queryStr.isEmpty()) users
                else users.filter {
                    it.fullName.contains(queryStr, true)
                }

        }

        mediatorResult.addSource(userItems) { filterF.invoke() }
        mediatorResult.addSource(query) { filterF.invoke() }
        return mediatorResult
        //return userItems
    }

    fun getSelectedData(): LiveData<List<UserItem>> = selectedItems

    fun handleSelectedItem(userId: String){

        userItems.value = userItems.value!!.map {
            if (it.id == userId) it.copy(isSelected = !it.isSelected)
            else it
        }

    }

    private fun loadUsers(): List<UserItem> = groupRepository.loadUsers().map{ it.toUserItem() }
    fun handleRemoveChip(userId: String){
        userItems.value = userItems.value!!.map {
            if (it.id == userId) it.copy(isSelected = false)
            else it
        }
    }

    fun handleSearchQuery(text: String) {
        query.value = text
    }


}
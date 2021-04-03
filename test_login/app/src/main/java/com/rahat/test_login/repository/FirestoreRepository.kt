package com.rahat.test_login.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.rahat.test_login.data.User


class FirestoreRepository {

    val TAG = "FIREBASE_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()

    fun saveUser(user: User): Task<Void> {
        // get a unique id
        val ref = firestoreDB.collection("users").document()
        user.id = ref.id
        // path  of the doc
        val documentReference = firestoreDB.collection("users").document(user.id)
        return documentReference.set(user)
    }

    // search for existing email address
    fun searchUserByEmail(email: String): Task<QuerySnapshot> {
        return firestoreDB.collection("users")
            .whereEqualTo("email", email)
            .get()
    }

    // get saved addresses from firebase
    fun loginUser(email: String, password: String): Task<QuerySnapshot> {
        return firestoreDB.collection("users")
            .whereEqualTo("email", email)
            .whereEqualTo("password" , password)
            .get()
    }


}
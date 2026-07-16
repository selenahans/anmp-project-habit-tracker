package com.example.habittracker.model

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: User)

    @Query("SELECT * FROM user")
    fun selectAllUser(): List<User>

    @Query("SELECT * FROM user WHERE uuid = :id")
    fun selectUser(id: Int): User

    @Query("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    fun login(username: String, password: String): User?

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)
    
    @Query("SELECT COUNT(*) FROM user")
    fun countUser(): Int
}
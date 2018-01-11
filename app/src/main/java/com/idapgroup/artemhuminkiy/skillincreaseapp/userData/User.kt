package com.idapgroup.artemhuminkiy.skillincreaseapp.userData

class User() {

    constructor(id: String, name: String, age: Int, gender : String) : this() {
        this.id = id
        this.name = name
        this.age = age
        this.gender = gender
    }

    lateinit var id : String
    lateinit var name : String
    var age : Int = 0
    lateinit var gender : String

    override fun toString(): String = "id=$id name =$name age=$age gender=$gender"
}
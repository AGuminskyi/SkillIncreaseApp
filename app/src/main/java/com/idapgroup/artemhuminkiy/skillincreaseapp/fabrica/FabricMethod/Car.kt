package com.idapgroup.artemhuminkiy.skillincreaseapp.fabrica.FabricMethod

abstract class Car(var name : String) {
    fun takeCar(){
        print("Buy car" + name)
    }

    fun driveCar(){
        print("Drive car" + name)
    }
}
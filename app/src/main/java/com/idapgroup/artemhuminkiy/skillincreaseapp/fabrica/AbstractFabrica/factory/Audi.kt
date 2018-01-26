package com.idapgroup.artemhuminkiy.skillincreaseapp.fabrica.AbstractFabrica.factory

import com.idapgroup.artemhuminkiy.skillincreaseapp.fabrica.AbstractFabrica.parts.body.Body
import com.idapgroup.artemhuminkiy.skillincreaseapp.fabrica.AbstractFabrica.parts.body.CircleBody
import com.idapgroup.artemhuminkiy.skillincreaseapp.fabrica.AbstractFabrica.parts.wheels.CircleWheels
import com.idapgroup.artemhuminkiy.skillincreaseapp.fabrica.AbstractFabrica.parts.wheels.Wheels

class Audi : CarFactory{
    override fun createWheel() : Wheels {
        return CircleWheels()
    }

    override fun createBody() : Body {
        return CircleBody()
    }
}
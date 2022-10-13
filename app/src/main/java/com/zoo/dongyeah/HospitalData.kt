package com.zoo.dongyeah

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "Response")
data class HospitalData(
    @Element
    val header: Header?,
    @Element
    val body: Body?,
)

@Xml (name = "header")
data class Header(
    @PropertyElement
    val resultCode: Int,
    @PropertyElement
    val resultMsg: String,
)

@Xml
data class Body(
    @Element(name = "item")
    val item: List<Item>
)

@Xml
data class Item(
    @PropertyElement(name = "yadmNm") var name: String?,
    @PropertyElement(name = "addr") var address: String?,
    @PropertyElement(name = "telno") var tel: String?,
    @PropertyElement(name = "XPos") var xPos: String?,
    @PropertyElement(name = "YPos") var yPos: String?,
)
package com.zoo.dongyeah

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "response")
data class HospitalData(
    @Element(name = "header")
    val header: Header,
    @Element(name = "body")
    val body: Body
) {

}

@Xml(name = "header")
data class Header(
    @PropertyElement(name = "resultCode")
    val resultCode: Int,
    @PropertyElement(name = "resultMsg")
    val resultMsg: String
)

@Xml(name = "body")
data class Body(
    @Element
    val items: Items
)

@Xml(name = "items")
data class Items(
    @Element(name = "item")
    val item: List<Item>
)

@Xml
data class Item(
    @PropertyElement(name = "ykiho") var kiho: String?,
    @PropertyElement(name = "yadmNm") var hosName: String?,
    @PropertyElement(name = "clCdNm") var hosCode: String?,
    @PropertyElement(name = "hospUrl") var hosUrl: String?,
    @PropertyElement(name = "addr") var address: String?,
    @PropertyElement(name = "telno") var tel: String?,
    @PropertyElement(name = "XPos") var xPos: Double?,
    @PropertyElement(name = "YPos") var yPos: Double?
)


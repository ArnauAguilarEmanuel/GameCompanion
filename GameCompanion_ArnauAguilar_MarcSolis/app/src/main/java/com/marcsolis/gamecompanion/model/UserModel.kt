package com.marcsolis.gamecompanion.model

data class item(
    val mainWeapon : String,
val mainWeaponURL : String,
val secondaryWeapon : String,
val secondaryWeaponURL : String,

val mainWeaponAcc1URL : String,
val mainWeaponAcc2URL : String,
val mainWeaponAcc3URL : String,

val secondaryWeaponAcc1URL : String,
val secondaryWeaponAcc2URL : String,

val className : String
){
    constructor():this("","","","","","","","","","className")
}

data class itemsClass(
    var class1: item,
    var class2: item,
    var class3: item,
    var class4: item,
    var class5: item,
    var class6: item,
    var class7: item,
    var class8: item,
    var class9: item,
    var class10: item
){
 constructor():this(item(),item(),item(),item(),item(),item(),item(),item(),item(),item())
}

data class UserModel(
    val userName : String,

    val iconURL : String,
    val prestigeName : String,
    val prestigeLevel : String,
    val kills : Int,
    val deaths : Int,
    val losses : Int,
    val wins : Int,
    val playTime : String,

    val userEmail : String,
    val userId : String,
    val score: Int,

    val classes: itemsClass
) {
    constructor() : this("","","","",0,0,0,0,"","","",0, itemsClass()){

    }
    public var list = ArrayList<item>()
}
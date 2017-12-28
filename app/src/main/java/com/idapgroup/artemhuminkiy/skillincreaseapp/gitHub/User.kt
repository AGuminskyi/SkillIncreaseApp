package com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class User(login : String = "", id : Long = -1, url : String = "", company : String = "", blog : String = "", email : String = "")
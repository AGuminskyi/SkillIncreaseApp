package com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub

import com.fasterxml.jackson.annotation.JsonProperty

//@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
open class Repository (
        var name : String = "",
        @JsonProperty("full_name")
        var fullName: String = "",
        var owner : User = User())
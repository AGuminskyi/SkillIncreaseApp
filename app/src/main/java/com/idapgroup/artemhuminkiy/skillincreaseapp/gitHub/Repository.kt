package com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
open class Repository(
        @JsonProperty("id")
        var id: String= "",
        @JsonProperty("name")
        var name: String = "",
        @JsonProperty("full_name")
        var fullName: String = "",
        @JsonProperty("owner")
        var owner: User = User())
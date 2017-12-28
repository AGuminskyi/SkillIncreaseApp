package com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class Repository (var name : String = "", var full_name: String = "")
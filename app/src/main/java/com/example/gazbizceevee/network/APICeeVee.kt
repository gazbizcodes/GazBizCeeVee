package com.example.gazbizceevee.network

data class curriculum_vitae(
    val personal_info: personal_info,
    val introduction: String,
    val development: String,
    val skills: List<skills>,
    val experience: List<experience>,
    val education: List<education>,
    val references: List<references>
)

data class personal_info(
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val city: String,
    val role_title: String
)

data class skills(
    val title: String,
    val skill_list: List<String>
)

data class experience(
    val from: String,
    val to: String,
    val company_name: String,
    val location: String,
    val title: String,
    val skills: List<String>,
    val description: String,
    val bullets: List<String>
)

data class education(
    val from: String,
    val to: String,
    val provider: String,
    val course_name: String,
    val description: String,
)

data class references(
    val name: String,
    val role: String,
    val relationship: String,
    val contact: String
)
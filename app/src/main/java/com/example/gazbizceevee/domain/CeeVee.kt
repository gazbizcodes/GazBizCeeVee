package com.example.gazbizceevee.network

import com.squareup.moshi.Json

data class CeeVee(
    @Json(name = "personal_info")
    val personalInfo: PersonalInfo,
    val introduction: String,
    val development: String,
    val skills: List<Skills>,
    val experience: List<Experience>,
    val education: List<Education>,
    val references: List<References>
)

@Json(name = "personal_info")
data class PersonalInfo(
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val city: String,
    @Json(name = "role_title")
    val roleTitle: String
)

@Json(name = "skills")
data class Skills(
    val title: String,
    @Json(name = "skill_list")
    val skillList: List<String>
)

@Json(name = "experience")
data class Experience(
    val from: String,
    val to: String,
    @Json(name = "company_name")
    val companyName: String,
    val location: String,
    val title: String,
    val skills: List<String>,
    val description: String,
    val bullets: List<String>
)

@Json(name = "education")
data class Education(
    val from: String,
    val to: String,
    val provider: String,
    @Json(name = "course_name")
    val courseName: String,
    val description: String,
)

@Json(name = "References")
data class References(
    val name: String,
    val role: String,
    val relationship: String,
    val contact: String
)
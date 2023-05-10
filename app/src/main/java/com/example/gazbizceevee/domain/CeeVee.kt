package com.example.gazbizceevee.domain

data class CeeVee(
    val personalInfo: PersonalInfo,
    val introduction: String,
    val development: String,
    val skills: List<Skill>,
    val experience: List<Experience>,
    val education: List<Education>,
    val references: List<Reference>
)

data class PersonalInfo(
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val city: String,
    val roleTitle: String
)

data class Skill(
    val title: String,
    val skillList: List<String>
)

data class Experience(
    val from: String,
    val to: String,
    val companyName: String,
    val location: String,
    val title: String,
    val skills: List<String>,
    val description: String,
    val bullets: List<String>
)

data class Education(
    val from: String,
    val to: String,
    val provider: String,
    val courseName: String,
    val description: String,
)

data class Reference(
    val name: String,
    val role: String,
    val relationship: String,
    val contact: String
)
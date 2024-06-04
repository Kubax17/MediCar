package pl.kucharski.autoservice

enum class ProductType(private val stringValue: String) {
    FOOD_AND_BEVERAGE("Food and Beverage"),
    CLOTHING_AND_ACCESSORIES("Clothing and Accessories"),
    HEALTH_AND_PERSONAL_CARE("Health and Personal Care"),
    HOME_AND_GARDEN("Home and Garden"),
    ELECTRONICS("Electronics"),
    SPORTS_AND_OUTDOOR_GEAR("Sports and Outdoor Gear"),
    BEAUTY_AND_GROOMING("Beauty and Grooming"),
    TRAVEL("Travel"),
    ENTERTAINMENT("Entertainment"),
    EDUCATION_AND_TRAINING("Education and Training"),
    TRANSPORTATION("Transportation"),
    CHARITABLE_DONATIONS("Charitable Donations"),
    SUBSCRIPTIONS("Subscriptions"),
    OFFICE_SUPPLIES("Office Supplies"),
    SCHOOL_SUPPLIES("School Supplies"),
    FINANCIAL_SERVICES("Financial Services");

    override fun toString(): String {
        return stringValue
    }
}
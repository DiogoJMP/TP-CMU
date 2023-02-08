package pt.ipp.estg.cmu.api.openchargemap

import com.google.gson.annotations.SerializedName

data class OCMResponse(
    @SerializedName("OperatorInfo") var OperatorInfo: OperatorInfo = OperatorInfo(),
    @SerializedName("UsageType") var UsageType: UsageType = UsageType(),
    @SerializedName("StatusType") var StatusType: StatusType = StatusType(),
    @SerializedName("IsRecentlyVerified") var IsRecentlyVerified: Boolean,
    @SerializedName("DateLastVerified") var DateLastVerified: String,
    @SerializedName("ID") var ID: Int,
    @SerializedName("UsageCost") var UsageCost: String,
    @SerializedName("AddressInfo") var AddressInfo: AddressInfo = AddressInfo(),
    @SerializedName("Connections") var Connections: ArrayList<Connections> = arrayListOf(),
    @SerializedName("NumberOfPoints") var NumberOfPoints: Int,
    @SerializedName("DateLastStatusUpdate") var DateLastStatusUpdate: String
)

data class OperatorInfo(
    @SerializedName("WebsiteURL") var WebsiteURL: String? = null,
    @SerializedName("Comments") var Comments: String? = null,
    @SerializedName("PhonePrimaryContact") var PhonePrimaryContact: String? = null,
    @SerializedName("PhoneSecondaryContact") var PhoneSecondaryContact: String? = null,
    @SerializedName("IsPrivateIndividual") var IsPrivateIndividual: Boolean? = null,
    @SerializedName("AddressInfo") var AddressInfo: String? = null,
    @SerializedName("BookingURL") var BookingURL: String? = null,
    @SerializedName("ContactEmail") var ContactEmail: String? = null,
    @SerializedName("FaultReportEmail") var FaultReportEmail: String? = null,
    @SerializedName("ID") var ID: Int? = null,
    @SerializedName("Title") var Title: String? = null
)

data class UsageType(
    @SerializedName("IsPayAtLocation") var IsPayAtLocation: Boolean? = null,
    @SerializedName("IsMembershipRequired") var IsMembershipRequired: Boolean? = null,
    @SerializedName("IsAccessKeyRequired") var IsAccessKeyRequired: Boolean? = null,
    @SerializedName("ID") var ID: Int? = null,
    @SerializedName("Title") var Title: String? = null
)

data class StatusType(
    @SerializedName("IsOperational") var IsOperational: Boolean? = null,
    @SerializedName("IsUserSelectable") var IsUserSelectable: Boolean? = null,
    @SerializedName("ID") var ID: Int? = null,
    @SerializedName("Title") var Title: String? = null
)

data class Country(
    @SerializedName("ISOCode") var ISOCode: String? = null,
    @SerializedName("ContinentCode") var ContinentCode: String? = null,
    @SerializedName("Title") var Title: String? = null
)

data class AddressInfo(
    @SerializedName("Title") var Title: String? = null,
    @SerializedName("AddressLine1") var AddressLine1: String? = null,
    @SerializedName("AddressLine2") var AddressLine2: String? = null,
    @SerializedName("Town") var Town: String? = null,
    @SerializedName("StateOrProvince") var StateOrProvince: String? = null,
    @SerializedName("Postcode") var Postcode: String? = null,
    @SerializedName("Country") var Country: Country? = Country(),
    @SerializedName("Latitude") var Latitude: Double? = null,
    @SerializedName("Longitude") var Longitude: Double? = null,
    @SerializedName("ContactTelephone1") var ContactTelephone1: String? = null,
    @SerializedName("ContactTelephone2") var ContactTelephone2: String? = null,
    @SerializedName("ContactEmail") var ContactEmail: String? = null,
    @SerializedName("RelatedURL") var RelatedURL: String? = null,
    @SerializedName("Distance") var Distance: Double? = null,
    @SerializedName("DistanceUnit") var DistanceUnit: Int? = null
)

data class ConnectionType(
    @SerializedName("FormalName") var FormalName: String? = null,
    @SerializedName("Title") var Title: String? = null
)

data class Level(
    @SerializedName("Comments") var Comments: String? = null,
    @SerializedName("IsFastChargeCapable") var IsFastChargeCapable: Boolean? = null,
    @SerializedName("ID") var ID: Int? = null,
    @SerializedName("Title") var Title: String? = null
)

data class CurrentType(
    @SerializedName("Description") var Description: String? = null,
    @SerializedName("ID") var ID: Int? = null,
    @SerializedName("Title") var Title: String? = null
)

data class Connections(
    @SerializedName("ID") var ID: Int? = null,
    @SerializedName("ConnectionType") var ConnectionType: ConnectionType? = ConnectionType(),
    @SerializedName("Reference") var Reference: String? = null,
    @SerializedName("StatusType") var StatusType: StatusType? = StatusType(),
    @SerializedName("Level") var Level: Level? = Level(),
    @SerializedName("Amps") var Amps: Int? = null,
    @SerializedName("Voltage") var Voltage: Int? = null,
    @SerializedName("PowerKW") var PowerKW: Int? = null,
    @SerializedName("CurrentType") var CurrentType: CurrentType? = CurrentType(),
    @SerializedName("Quantity") var Quantity: Int? = null
)
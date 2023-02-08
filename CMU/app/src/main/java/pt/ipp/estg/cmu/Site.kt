package pt.ipp.estg.cmu

import pt.ipp.estg.cmu.api.openchargemap.AddressInfo
import pt.ipp.estg.cmu.api.openchargemap.StatusType

data class Site(val addressInfo: AddressInfo, val status: StatusType)

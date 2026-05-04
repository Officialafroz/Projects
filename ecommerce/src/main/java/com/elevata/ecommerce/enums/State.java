package com.elevata.ecommerce.enums;

public enum State {
    ANDHRA_PRADESH,
    ARUNACHAL_PRADESH,
    ASSAM,
    BIHAR,
    CHHATTISGARH,
    GOA,
    GUJARAT,
    HARYANA,
    HIMACHAL_PRADESH,
    JHARKHAND,
    KARNATAKA,
    KERALA,
    MADHYA_PRADESH,
    MAHARASHTRA,
    MANIPUR,
    MEGHALAYA,
    MIZORAM,
    NAGALAND,
    ODISHA,
    PUNJAB,
    RAJASTHAN,
    SIKKIM,
    TAMIL_NADU,
    TELANGANA,
    TRIPURA,
    UTTAR_PRADESH,
    UTTARAKHAND,
    WEST_BENGAL;

    public static State fromString(String state) {
        if (state == null || state.isEmpty()) {
            throw new IllegalArgumentException("State cannot be null or empty.");
        }

        String normalize = state.trim().toUpperCase().replace(" ", "_");

        return State.valueOf(normalize);
    }
}

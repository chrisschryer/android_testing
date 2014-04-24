package com.cschryer.eaterofjsons.classes;

import org.json.JSONException;
import org.json.JSONObject;

public class SpecialOffer {

    
    // JSON keys
    public static final String SpecialOffer_USER_DISPLAY_NAME = "username";//inside user/
    public static final String SpecialOffer_USER_name = "name";//inside user/
    public static final String SpecialOffer_TEXT_DESCRIPTION = "attrib";
    public static final String SpecialOffer_TEXT_SPONSOR = "desc";
    public static final String SpecialOffer_USER_IMAGE_URL = "src"; //inside user/avatar/
    public static final String SpecialOffer_USER_IMAGE_WIDTH = "width"; //inside user/avatar/
    public static final String SpecialOffer_USER_IMAGE_HEIGHT = "height"; //inside user/avatar/
    public static final String SpecialOffer_OFFER_IMAGE_URL = "src";
    public static final String SpecialOffer_HREF = "href";
    

    private String strUserDisplayName;
    private String strUserName;
    private String strOfferDescription;
    private String strOfferSponsor;
    private String strUserImgURL;
    private Integer userImgHeight;
    private Integer userImgWidth;
    private String strOfferImgURL;
    private String strOfferHREF;
    
    
    public SpecialOffer(JSONObject jsonObj) {
        if(jsonObj != null){
        	
            try {
            	JSONObject user = jsonObj.getJSONObject("user");
				setStrUserDisplayName(user.optString(SpecialOffer_USER_DISPLAY_NAME, ""));
				setStrUserName(user.optString(SpecialOffer_USER_name, ""));
				setUserImageUrl(user.optString(SpecialOffer_USER_IMAGE_URL, ""));
				setUserImgHeight(user.optInt(SpecialOffer_USER_IMAGE_HEIGHT, 250));
				setUserImgWidth(user.optInt(SpecialOffer_USER_IMAGE_WIDTH, 250));
				
				setStrOfferDescription(jsonObj.optString(SpecialOffer_TEXT_DESCRIPTION, ""));
				setStrOfferSponsor(jsonObj.optString(SpecialOffer_TEXT_SPONSOR));
				setStrOfferImgURL(jsonObj.optString(SpecialOffer_OFFER_IMAGE_URL));
				setStrOfferHREF(jsonObj.optString(SpecialOffer_HREF));
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    
    public SpecialOffer() {
    }
    
    //Getters & Setters
    

    public String getUserImageUrl() {
        return strUserImgURL;
    }

    public void setUserImageUrl(String imageUrl) {
        this.strUserImgURL = imageUrl;
    }

	public String getStrOfferSponsor() {
		return strOfferSponsor;
	}

	public void setStrOfferSponsor(String strOfferSponsor) {
		this.strOfferSponsor = strOfferSponsor;
	}

	public Integer getUserImgHeight() {
		return userImgHeight;
	}

	public void setUserImgHeight(Integer userImgHeight) {
		this.userImgHeight = userImgHeight;
	}

	public Integer getUserImgWidth() {
		return userImgWidth;
	}

	public void setUserImgWidth(Integer userImgWidth) {
		this.userImgWidth = userImgWidth;
	}

	public String getStrOfferImgURL() {
		return strOfferImgURL;
	}

	public void setStrOfferImgURL(String strOfferImgURL) {
		this.strOfferImgURL = strOfferImgURL;
	}

	public String getStrOfferHREF() {
		return strOfferHREF;
	}

	public void setStrOfferHREF(String strOfferHREF) {
		this.strOfferHREF = strOfferHREF;
	}

	public String getStrUserDisplayName() {
		return strUserDisplayName;
	}

	public void setStrUserDisplayName(String strUserDisplayName) {
		this.strUserDisplayName = strUserDisplayName;
	}

	public String getStrUserName() {
		return strUserName;
	}

	public void setStrUserName(String strUserName) {
		this.strUserName = strUserName;
	}

	public String getStrOfferDescription() {
		return strOfferDescription;
	}

	public void setStrOfferDescription(String strOfferDescription) {
		this.strOfferDescription = strOfferDescription;
	}


}




package umn.ac.id.level;

@SuppressWarnings("ALL")
public class ExploreItem
{
    String user, location, profileImg, locationImg, id;
    int travelDays, totalCost;

    public ExploreItem() {}

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getProfileImg()
    {
        return profileImg;
    }

    public void setProfileImg(String profileImg)
    {
        this.profileImg = profileImg;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public int getTravelDays()
    {
        return travelDays;
    }

    public void setTravelDays(int travelDays)
    {
        this.travelDays = travelDays;
    }

    public int getTotalCost()
    {
        return totalCost;
    }

    public void setTotalCost(int totalCost)
    {
        this.totalCost = totalCost;
    }

    public String getLocationImg()
    {
        return locationImg;
    }

    public void setLocationImg(String locationImg)
    {
        this.locationImg = locationImg;
    }
}

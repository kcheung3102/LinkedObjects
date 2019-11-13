public class Link {
    private long id;
    private long companyId;             // the company's id
    private long personId;              // the person's id
    private String startDate;           // date when person started at company
    private String role;                // person's role at company

    public Link() {}

    public Link(long id, long companyId, long personId, String startDate, String role) {
        this.id = id;
        this.companyId = companyId;
        this.personId = personId;
        this.startDate = startDate;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

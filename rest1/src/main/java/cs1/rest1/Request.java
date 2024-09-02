package cs1.rest1;

public class Request {
    private int requestId;
    private String requestFrom;
    private String requestedResource;
    private String requestStatus;

    // Constructor
    public Request(int requestId, String requestFrom, String requestedResource, String requestStatus) {
        this.requestId = requestId;
        this.requestFrom = requestFrom;
        this.requestedResource = requestedResource;
        this.requestStatus = requestStatus;
    }

    public Request() {
		// TODO Auto-generated constructor stub
	}

	// Getters and Setters (optional)
    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getRequestFrom() {
        return requestFrom;
    }

    public void setRequestFrom(String requestFrom) {
        this.requestFrom = requestFrom;
    }

    public String getRequestedResource() {
        return requestedResource;
    }

    public void setRequestedResource(String requestedResource) {
        this.requestedResource = requestedResource;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestId='" + requestId + '\'' +
                ", requestFrom='" + requestFrom + '\'' +
                ", requestedResource='" + requestedResource + '\'' +
                ", requestStatus='" + requestStatus + '\'' +
                '}';
    }
}


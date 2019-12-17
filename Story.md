As part of our mission to revolutionise healthcare we have to adapt to external systems to get a wide range of services to our customers.

We have recently have begun integrating with a third party provider that provides Doctors appointments as-a-appointmentService. 
Unfortunately the API has a few problems with scheduling appointments at the right times and the data is often corrupt. 
However we have no control over the API and have a tight deadline to get integrated.

The API consists of a simple GET that can be found at

https://us-central1-sesame-care-dev.cloudfunctions.net/sesame_programming_test_api

Your task is to clean up and restructure the data for use by sesame!

Additionally we need to flag all the errors so we can feedback to the third party.

Please construct a simple API that hosts the appointmentService to clean up the data, the API should have a simple endpoint that 
replies with the processed data.

The final structure of the data should be :

…For each doctor

```json
[
  {
    "firstName": "First name",
    "lastName": "Last name",
    "appointmentsByLocation": [
      {
        "locationName": "Location name",
        "appointments": [
          {
            "appointmentId": "appointment-1234",
            "startDateTime": "2019-12-05T15:06:34.001Z",
            "appointmentService": {
              "serviceName": "Service name",
              "price": "Price"
            }
          }
        ]
      }
    ]
  }
]
```

Additionally there should be an errors section in the response which contains errors that are discovered for each
 appointment affected. The errors should be grouped by type with a classifier field (e.g a string describing the error
 type).

You can be creative with how this should look.

Please provide a git repository containing the source and instructions on how to build + run the application. (Docker 
containers are also great)

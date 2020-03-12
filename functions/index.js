const functions = require('firebase-functions');

const admin = require('firebase-admin');
const serviceAccount = require("./walk-walk-revolution-a0e68-firebase-adminsdk-s4mpm-d58fd71325.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://walk-walk-revolution-a0e68.firebaseio.com"
});

exports.sendNotification = functions.firestore
    .document("/messages/{newKey}")
    .onCreate((snap, context) => {

    const document = snap.exists ? snap.data() : null;

    if(document) {
        // Get receive user email
        const receiverEmail = document.receiverEmail;
        console.log("receiverEmail: ", receiverEmail);

        // Get receive user name
        const receiverName = document.receiverName;
        console.log("receiverName: ", receiverName);

        // Get send user email
        const senderEmail = document.senderEmail;
        console.log("senderEmail: ", senderEmail);

        // Get type of message
        const messageType = document.messageType;
        console.log("messageType: ", messageType);

        const routeName=document.routeName;

        const month=document.month;

        const day=document.day;

        const hour=document.hour;
        // TODO check message type accordingly

        //query the users node and get the name of the user who sent the message
        console.log("Path: ", "/users/" + senderEmail.replace(".", ","));
        return admin.firestore().collection("users").doc(senderEmail.replace(".", ",")).get('server').then(snap => {
            const senderInfo = snap.exists ? snap.data() : null;

            if(!senderInfo) {
                console.log("Snapshot data is null");
                return null;
            }

            /*console.log("Null snap: ", snap.key);
            console.log("Snap: ", snap.exists());
            console.log("Snap val: ", snap.child("routes/").exists());
            console.log("Snap child val: ", snap.child("routes").val());*/
            console.log(senderInfo);
            console.log("first_name: ", snap.get("first_name"));
            console.log("last_name: ", snap.get("last_name"));
            const senderName = snap.get("first_name") + " " + snap.get("last_name");
            console.log("senderName: ", senderName);

            //get the token of the user receiving the message
            return admin.firestore().collection("users").doc(receiverEmail.replace(".", ",")).get('server').then(snap => {
                // Check user name and email match
                const receiverName = snap.get("first_name") + " " + snap.get("last_name");
                if(receiverName !== receiverName) {
                    console.log("Name doesn't match");
                    return null;
                } else {
                    const token = snap.get("token");
                    console.log("token: ", token);

                    // Build and send message
                    let sentence="has sent you an invitation! Click to accept.";
                    if(messageType==="Team Walk Invitation"){
                    sentence=" proposed "+ routeName+" on "+month+" "+day+" at "+hour;
                    }
                    const message = {
                        notification: {
                            title: "A friend has sent you an invitation!",
                            body: "Your friend " + senderName + sentence
                        },
                        data: {
                            messageType: messageType,
                            senderName: senderName,
                            senderEmail: senderEmail
                        }
                    };

                    return admin.messaging().sendToDevice(token, message)
                        .then(response => {
                            console.log("Successfully sent message:", response);
                            return null;
                          })
                          .catch(function(error) {
                            console.log("Error sending message:", error);
                          });
                }
            })
            .catch(reason => {
                console.log("Error Reason: ", reason);
            });
        })
        .catch(reason => {
            console.log(reason);
            return null;
        });
    }
});

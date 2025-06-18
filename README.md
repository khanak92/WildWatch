# 🐾 WildWatch – Real-Time Wildlife Detection System

WildWatch is a smart Android-based surveillance system designed to detect and alert users about wild animal presence in real-time using AI-powered object detection. It aims to reduce human-wildlife conflict in urban and rural areas near wildlife habitats.

---

## 🚨 Problem Statement
Wild animals such as leopards, monkeys, and wild boars often enter human-populated areas, posing a threat to lives and property. Existing CCTV systems lack real-time AI capabilities. WildWatch fills this gap by offering live detection, alerts, logging, and visualization.

---

## 🎯 Features
- 📷 **Real-time RTSP camera feed integration**
- 🧠 **YOLOv8-based object detection** (Leopard, Monkey, Wild Boar, Armed Human)
- 🔔 **Instant full-screen alert with vibration and sound**
- 🌐 **Firebase Realtime Database integration**
- 📍 **Map view showing detection locations**
- 📊 **Trend analysis using monthly and yearly detection graphs**
- 🔕 **Mute/unmute feature with local preference saving**
- 📹 **Video and text-based precautionary guidelines**

---

## 🧰 Tech Stack

| Component      | Technology                     |
|----------------|---------------------------------|
| Frontend       | Kotlin (Android Studio)         |
| AI Model       | YOLOv8 (Ultralytics), Python    |
| Database       | Firebase Realtime Database      |
| Streaming      | RTSP, ExoPlayer                 |
| UI             | Material Design, Lottie         |
| Mapping        | Google Maps API                 |
| Charts         | GraphView / MPAndroidChart      |

---

## 🧠 AI Model Training

- Dataset: Collected from Kaggle, Roboflow
- Annotated using: **LabelImg**
- Classes: Leopard, Monkey, Wild Boar, Armed Human
- Trained for 50+ epochs using YOLOv8
- Evaluation Metrics:
  - Precision: ~82%
  - Recall: ~88%
  - F1 Score: ~85%
  - mAP@0.5: ~90%

---

## 🖥️ System Architecture

Camera → YOLOv8 Model (Python) → Detection Result → Firebase → Android UI + Notification

---

##  Future Enhancements

Raspberry Pi / Jetson Nano edge deployment

Push notifications

User login system

More animal classes

Offline detection storage + sync



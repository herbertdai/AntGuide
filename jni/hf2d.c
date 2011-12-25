/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
#include <string.h>
#include <stdio.h>
#include <math.h>
#include <jni.h>
#include "hf2d.h"

#include <android/log.h>
#define LOG_TAG "hf2d jni >>>>"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)


jstring JNICALL Java_com_howfun_android_antguide_hf2djni_hf2d_stringFromJNI
(JNIEnv *env, jobject thiz) {
   return (*env)->NewStringUTF(env, "Hello from JNI !");
}

JNIEXPORT jstring JNICALL Java_com_howfun_android_antguide_hf2djni_hf2d_accessField
(JNIEnv *env, jobject obj){
   jfieldID fid;
   jstring jstr;
   const char *str;
   //get a ref to class
   jclass cls = (*env)->GetObjectClass(env, obj);
   fid = (*env)->GetFieldID(env,cls, "s", "Ljava/lang/String;");
   if (fid == NULL) {
      LOGE("GetFieldID is null!\n");
   }

   jstr = (*env)->GetObjectField(env, obj, fid);

   str = (*env)->GetStringUTFChars(env, jstr, NULL);
   if (str == NULL) {
      LOGE("GetStringUTFChars return null, OOM");
   }
   LOGD("in C, field is : %s\n", str);
   (*env)->ReleaseStringUTFChars(env, jstr, str);

   jstr = (*env)->NewStringUTF(env, "123");
   if (jstr == NULL) {
      LOGE("NewStringUTF return null, OOM");
   }

   (*env)->SetObjectField(env, obj, fid, jstr);

}

#define Assert(x) if (x == NULL) { \
   LOGE("Get x error!!\n"); \
} else {LOGE("get x ok\n");}

/*
 * Class:     com_howfun_android_antguide_hf2djni_hf2d
 * Method:    checkRectAndLineCollisionMirror
 * Signature: (Lcom/howfun/android/HF2D/AntSprite;Lcom/howfun/android/HF2D/LineSprite;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_howfun_android_antguide_hf2djni_hf2d_checkRectAndLineCollisionMirror
(JNIEnv *env, jobject thisObj, jobject antObj, jobject lineObj) {
   jfloat angle = 0;

   jclass antRectClass;
   jobject antRectObj;
   jfieldID antRectLeftID, antRectTopID, antRectRightID, antRectBottomID;
   jint x1, x2, x3, y1, y2, y3;  //ant rect

   jclass PosClass;
   jclass LineSpriteClass;
   jobject lineStartPosObj, lineEndPosObj;
   jfieldID posXID, posYID;
   jfloat lax, lay, lbx, lby;

   jmethodID setAngleID;

   float newAngle;

   //Get method: ant.getAngle()
   jclass antClass = (*env)->GetObjectClass(env, antObj);
   if (antClass == NULL) {
      LOGE("Get antClass is null!\n");
      return JNI_FALSE;
   }
   jfieldID angleID = (*env)->GetFieldID(env, antClass, "mAngle", "F");
   if (angleID == NULL) {
      LOGE("Get angleID is null!\n");
      return JNI_FALSE;
   }
   angle = (*env)->GetFloatField(env, antObj, angleID);
   newAngle = angle;

   //get ant.mRect
   //x1 = left
   jfieldID antRectID = (*env)->GetFieldID(env, antClass, "mRect", "Landroid/graphics/Rect;");
   antRectObj = (*env)->GetObjectField(env, antObj, antRectID);
   if (antRectObj == NULL) {
      LOGE("Get ant rect is null\n");
      return JNI_FALSE;
   }
   antRectClass = (*env)->GetObjectClass(env, antRectObj);
   antRectLeftID = (*env)->GetFieldID(env, antRectClass, "left", "I");
   if (antRectLeftID == NULL) {
      LOGE("Get ant rect left ID is null\n");
      return JNI_FALSE;
   }
   x1 = (*env)->GetIntField(env, antRectObj, antRectLeftID);

   //y1 = top
   antRectTopID = (*env)->GetFieldID(env, antRectClass, "top", "I");
   if (antRectTopID== NULL) {
      LOGE("Get ant rect top ID is null\n");
      return JNI_FALSE;
   }
   y1 = (*env)->GetIntField(env, antRectObj, antRectTopID);

   x2 = x1;

   //y2 = bottom
   antRectBottomID = (*env)->GetFieldID(env, antRectClass, "bottom", "I");
   if (antRectBottomID == NULL) {
      LOGE("Get ant rect bottom ID is null\n");
      return JNI_FALSE;
   }
   y2 = (*env)->GetIntField(env, antRectObj, antRectBottomID);

   //x3 = right
   antRectRightID = (*env)->GetFieldID(env, antRectClass, "right", "I");
   if (antRectRightID == NULL) {
      LOGE("Get ant rect right ID is null\n");
      return JNI_FALSE;
   }
   x3 = (*env)->GetIntField(env, antRectObj, antRectRightID);
   y3 = y1;

   //get line.getStart().x .y
   LineSpriteClass = (*env)->GetObjectClass(env, lineObj);
   jmethodID getStartID = (*env)->GetMethodID(env, LineSpriteClass, "getStart", "()Lcom/howfun/android/hf2d/Pos;");
   if (getStartID == NULL) {
      LOGE("Get getStart() ID is null\n");
      return JNI_FALSE;
   }
   lineStartPosObj = (*env)->CallObjectMethod(env, lineObj, getStartID); 

   jmethodID getEndID = (*env)->GetMethodID(env, LineSpriteClass, "getEnd", "()Lcom/howfun/android/hf2d/Pos;");
   if (getEndID == NULL) {
      LOGE("Get getEnd() ID is null\n");
      return JNI_FALSE;
   }
   lineEndPosObj = (*env)->CallObjectMethod(env, lineObj, getEndID); 

   PosClass = (*env)->GetObjectClass(env, lineStartPosObj); 
   posXID = (*env)->GetFieldID(env, PosClass, "x", "F");
   if (posXID == NULL) {
      LOGE("Get posXID is null\n");
   }
   posYID = (*env)->GetFieldID(env, PosClass, "y", "F");
   if (posYID == NULL) {
      LOGE("Get posYID is null\n");
   }

   lax = (*env)->GetFloatField(env, lineStartPosObj, posXID);
   lay = (*env)->GetFloatField(env, lineStartPosObj, posYID);
   //get line.getEnd().x .y
   lbx = (*env)->GetFloatField(env, lineEndPosObj, posXID);
   lby = (*env)->GetFloatField(env, lineEndPosObj, posYID);

   setAngleID = (*env)->GetMethodID(env, antClass, "setAngle", "(F)V");
   if (setAngleID == NULL) {
      LOGE("Get setAngleID is null \n");
   }

   jboolean isCollision = JNI_FALSE;
   jboolean isUpperCollision = JNI_FALSE;

   // check upper horizontal side
   float rx = (y1 - lay) * (lbx - lax) / (lby - lay) + lax;

   float a = 0, b = 0;
   if (lax < lbx) {
      a = lax;
      b = lbx;
   } else {
      b = lax;
      a = lbx;
   }
   if (rx >= x1 && rx <= x3 && rx >= a && rx <= b) {
      isCollision = JNI_TRUE;
      isUpperCollision = JNI_TRUE;

   }
   // check lower horizontal side
   rx = (y2 - lay) * (lbx - lax) / (lby - lay) + lax;
   if (rx >= x1 && rx <= x3 && (rx >= a && rx <= b)) {
      isCollision = JNI_TRUE;
   }

   if (isCollision) {
      // set line two point a new sequence: smaller up, bigger down
      if (lay > lby) {
	 // swap
	 float temp = lax;
	 lax = lbx;
	 lbx = temp;

	 temp = lay;
	 lay = lby;
	 lby = temp;
      }

      //double lineAngle = Math.atan2((lby - lay), (lbx - lax));
      double lineAngle = atan2((lby - lay), (lbx - lax));
      //float degree = (float) Math.toDegrees(lineAngle);
      float degree = (float) lineAngle * 180 / 3.1415926; 

      //new mirror angle = lineangle + (lineangle - antangle)
      newAngle = degree + (degree - angle);
      //float angle = degree + (degree - ant.getAngle());

      LOGD("set angle = %f\n", newAngle);
      //: ant.setAngle(newAngle);
      (*env)->CallVoidMethod(env, antObj, setAngleID, newAngle);

   }

   // fix bug: horizontal line can't be checked
   if (!isCollision) {
      float ry = (x2 - lax) * (lby - lay) / (lbx - lax) + lay;
      if (lay < lby) {
	 a = lay;
	 b = lby;
      } else {
	 a = lby;
	 b = lay;
      }
      if ((ry >= y1) && (ry <= y2) && (ry >= a) && (ry <= b)) {
	 isCollision = JNI_TRUE;
      }
      if (isCollision) {

	 //newAngle = - ant.getAngle();
	 newAngle = - angle; 
	 //ant.setAngle(newAngle);
	 (*env)->CallVoidMethod(env, antObj, setAngleID, newAngle);
      }
   }

   return isCollision;

}


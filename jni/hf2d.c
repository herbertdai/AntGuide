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
#include <jni.h>
#include "hf2d.h"

#include <android/log.h>
#define LOG_TAG "hf2dLog>>>>"
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



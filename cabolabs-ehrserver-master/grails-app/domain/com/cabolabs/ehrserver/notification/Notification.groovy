/*
 * Copyright 2011-2020 CaboLabs Health Informatics
 *
 * The EHRServer was designed and developed by Pablo Pazos Gutierrez <pablo.pazos@cabolabs.com> at CaboLabs Health Informatics (www.cabolabs.com).
 *
 * You can't remove this notice from the source code, you can't remove the "Powered by CaboLabs" from the UI, you can't remove this notice from the window that appears then the "Powered by CaboLabs" link is clicked.
 *
 * Any modifications to the provided source code can be stated below this notice.
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
 */

package com.cabolabs.ehrserver.notification

class Notification {

   String name
   String language // language of the text
   String text
   String kind = 'web'

   String forSection // ehrs, contributions, versions, directory, queries, templates, users, roles, organizations, notifications
   String forOrganization
   String forUser // user.uid

   Date timestamp = new Date()

   boolean sent = false // used by the job that creates the notification statuses

   static constraints = {
      forSection nullable: true
      forOrganization nullable: true
      forUser nullable: true
      kind inList: ['web', 'email']
   }

   static lastNotifications(String forSection, String forOrganization, String forUser, String lang, int last = 15)
   {
      def c = NotificationStatus.createCriteria()
      def list = c.list {
         notification {
           eq('language', lang)
         }
         if (forUser)
         {
            or
            {
               user {
                  eq('uid', forUser)
               }
               notification {
                  isNull('forUser')
               }
            }
         }
         if (forOrganization) // forOrganization alwas comes, but it should match also when it is null on the notification
         {
            or {
               notification {
                  eq('forOrganization', forOrganization)
               }
               notification {
                  isNull('forOrganization')
               }
            }
         }
         if (forSection) // forSection always comes, but if should match also when it is null on the notification
         {
            or {
               notification {
                  eq('forSection', forSection)
               }
               notification {
                  isNull('forSection')
               }
            }
         }
         maxResults(last)
         notification {
            order("timestamp", "desc")
         }
      }

      return list
   }

   static newNotifications(String forSection, String forOrganization, String forUser, String lang)
   {
      def c = NotificationStatus.createCriteria()
      def list = c.list {
         eq('status', 'new')
         notification {
           eq('language', lang)
         }
         if (forUser)
         {
            or
            {
               user {
                  eq('uid', forUser)
               }
               notification {
                  isNull('forUser')
               }
            }
         }
         if (forOrganization) // forOrganization alwas comes, but it should match also when it is null on the notification
         {
            or {
               notification {
                  eq('forOrganization', forOrganization)
               }
               notification {
                  isNull('forOrganization')
               }
            }
         }
         if (forSection) // forSection always comes, but if should match also when it is null on the notification
         {
            or {
               notification {
                  eq('forSection', forSection)
               }
               notification {
                  isNull('forSection')
               }
            }
         }
      }

      return list
   }
}

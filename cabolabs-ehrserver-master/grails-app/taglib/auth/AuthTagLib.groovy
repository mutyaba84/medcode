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

package auth

import com.cabolabs.security.*

class AuthTagLib {

   def authService

   def loggedUserAttribute = { attr, body ->

      def sessman = SessionManager.instance
      def sess = sessman.getSession(session.id.toString())
      if (sess) // not logged in?
         out << sess.payload.user[attr.field]
   }

   def ifLoggedIn = { attrs, body ->

      def sessman = SessionManager.instance
      def sess = sessman.getSession(session.id.toString())
      if (sess) out << body()
   }

   def ifNotLoggedIn = { attrs, body ->

      def sessman = SessionManager.instance
      def sess = sessman.getSession(session.id.toString())
      if (!sess) out << body()
   }

   def userHasAnyRole = { attrs, body ->

      if (!attrs.roles) throw new Exception('roles attribute is required')

      if (authService.loggedInUserHasAnyRole(attrs.roles)) out << body()

      // def _roles = attrs.roles.split(",")
      //
      // def sessman = SessionManager.instance
      // def sess = sessman.getSession(session.id.toString())
      // def user_roles = sess.payload.user.getAuthorities(session.organization)
      // def has_role = false
      // user_roles.each { role ->
      //    if (_roles.contains(role.authority))
      //    {
      //       has_role = true
      //       return
      //    }
      // }
      //
      // if (has_role) out << body()
   }

   def userDoesntHaveRole = { attrs, body ->

      if (!attrs.role) throw new Exception('roles attribute is required')

      def _role = attrs.role

      def sessman = SessionManager.instance
      def sess = sessman.getSession(session.id.toString())
      def user_roles = sess.payload.user.getAuthorities(session.organization)
      def has_role = false
      user_roles.each { role ->
         if (role.authority == _role)
         {
            has_role = true
            return
         }
      }

      if (!has_role) out << body()
   }
}

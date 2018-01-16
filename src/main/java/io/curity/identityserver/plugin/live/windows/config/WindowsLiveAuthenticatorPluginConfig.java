/*
 *  Copyright 2017 Curity AB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.curity.identityserver.plugin.live.windows.config;

import se.curity.identityserver.sdk.config.Configuration;
import se.curity.identityserver.sdk.config.annotation.DefaultBoolean;
import se.curity.identityserver.sdk.config.annotation.DefaultEnum;
import se.curity.identityserver.sdk.config.annotation.Description;
import se.curity.identityserver.sdk.service.ExceptionFactory;
import se.curity.identityserver.sdk.service.HttpClient;
import se.curity.identityserver.sdk.service.Json;
import se.curity.identityserver.sdk.service.SessionManager;
import se.curity.identityserver.sdk.service.WebServiceClientFactory;
import se.curity.identityserver.sdk.service.authentication.AuthenticatorInformationProvider;

import java.util.Optional;

@SuppressWarnings("InterfaceNeverImplemented")
public interface WindowsLiveAuthenticatorPluginConfig extends Configuration
{
    @Description("Client ID")
    String getClientId();

    @Description("Client secret")
    String getClientSecret();

    @Description("Request a scope (wl.offline_access) that gives the ability to read and update a user's info at any time. Without this scope, an app can access the user's info only while the user is signed in to Live Connect and is using your app.")
    @DefaultBoolean(false)
    boolean isOfflineAccount();

    @Description("Request a scope (wl.signin), with single sign-in, users who are already signed in to Live Connect are also signed in to your website.")
    @DefaultBoolean(false)
    boolean isSingleSignin();

    @Description("Request a scope (wl.birthday) that grants read access to a user's birthday info including birth day, month, and year.")
    @DefaultBoolean(false)
    boolean isBirthdayInfo();

    enum Access
    {
        NONE, READ, WRITE
    }

    @Description("Manage access to a user's calendars and events.")
    @DefaultEnum("NONE")
    Access getCalendarsInfo();

    @Description("Request a scope (wl.contacts_birthday) that grants read access to the birth day and birth month of a user's contacts. Note that this also gives read access to the user's birth day, birth month, and birth year.")
    @DefaultBoolean(false)
    boolean isContactsBirthday();

    @Description("Request a scope (wl.contacts_create) that grants access to create new contacts in the user's address book.")
    @DefaultBoolean(false)
    boolean isContactsCreate();

    @Description("Request a scope (wl.contacts_calendars) that grants read access to a user's calendars and events. Also enables read access to any calendars and events that other users have shared with the user.")
    @DefaultBoolean(false)
    boolean isCalendarAndEvents();

    @Description("Request a scope (wl.contacts_photos) that grants read access to a user's albums, photos, videos, and audio, and their associated comments and tags. Also enables read access to any albums, photos, videos, and audio that other users have shared with the user.")
    @DefaultBoolean(false)
    boolean isContactsPhotos();

    @Description("Request a scope (wl.contacts_skydrive) that grants read access to Microsoft OneDrive files that other users have shared with the user. Note that this also gives read access to the user's files stored in OneDrive.")
    @DefaultBoolean(false)
    boolean isContactsOneDrive();

    @Description("Request a scope (wl.emails) that grants read access to a user's personal, preferred, and business email addresses.")
    @DefaultBoolean(false)
    boolean isEmailsAccess();

    @Description("Request a scope (wl.events_create) that grants access to create events on the user's default calendar.")
    @DefaultBoolean(false)
    boolean isEventsCreate();

    @Description("Request a scope (wl.imap) that grants read and write access to a user's email using IMAP, and send access using SMTP.")
    @DefaultBoolean(false)
    boolean isIMAP();

    @Description("Request a scope (wl.phone_numbers) that grants read access to a user's personal, business, and mobile phone numbers.")
    @DefaultBoolean(false)
    boolean isPhoneNumbersAccess();

    @Description("Request a scope (wl.photos) that grants read access to a user's photos, videos, audio, and albums.")
    @DefaultBoolean(false)
    boolean isPhotosAccess();

    @Description("Request a scope (wl.postal_addresses) that grants read access to a user's postal addresses.")
    @DefaultBoolean(false)
    boolean isPostalAddresses();

    @Description("Manage access to a user's files stored in OneDrive.")
    @DefaultEnum("NONE")
    Access getOneDriveAccess();

    @Description("Request a scope (wl.work_profile) that grants read access to a user's employer and work position information.")
    @DefaultBoolean(false)
    boolean isWorkProfileInfo();

    @Description("Request a scope (office.onenote_create) that grants read and write access to a user's OneNote notebooks stored in OneDrive.")
    @DefaultBoolean(false)
    boolean isOneNoteAccess();

    @Description("The HTTP client with any proxy and TLS settings that will be used to connect to windows live")
    Optional<HttpClient> getHttpClient();

    SessionManager getSessionManager();

    ExceptionFactory getExceptionFactory();

    AuthenticatorInformationProvider getAuthenticatorInformationProvider();

    WebServiceClientFactory getWebServiceClientFactory();

    Json getJson();

}

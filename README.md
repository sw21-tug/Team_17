# Login Sesame - Yet another FOSS Password Manager

**Login Sesame** is meant to be a Free and Open Source password manager for **Android**.

<img src="https://i.postimg.cc/ydrZsWh9/login-sesame-icon.png" width="150">

Among its main features, we should implement:
  
  * User friendly UI
  * Locally stored, ecrypted password vault
  * Secure, random password generator with different features
  * Secure notes area
  * Biometric app login (e.g. with fingerprint sensor if available)
  * Easy copy user/password to clipboard
  * ...

## Long term vision
After the Android application already has all the major features implemented, the long
term vision of the project is to develop a server application to which the Android app
would connect and the user could automatically sync the vault over multiple devices.

### Team members

| Role             | Name                  | 
| :---             |    :----:             |
| **Product Owner**|   Alexandru Agape     |
| **Scrum Master** |   Darja Bistrova      |
| **Developer**    | Johannes Becker       |
| **Developer**    | Christina Sophie Knes |
| **Developer**    | Bernhard Paul Lippe   |
| **Developer**    | Tobias Pichler        |
| **Developer**    | Lukas Preitler        |
| **Developer**    | Thomas Reith          |

## Implemented features
| Ticket #       | Name                                      | 
| :---           |    :----:                                 |
| _**LS-026**_   |    _**Basic Application**_                |
| _**LS-001**_   |    _**Account Creation**_                 |
| LS-001-A       |    Create Startup Activity                |
| LS-001-B       |    Basic Database                         |
| LS-001-C       |    Linking UI and Database                |
| _**LS-002**_   |    _**Passwords Overview**_               |
| LS-002-A       |    Passwords Overview UI                  |
| LS-002-B       |    Vault entry Database interface         |
| _**LS-007**_   |    _**Application Login**_                |
| LS-007-A       |    Login Activity UI                      |
| LS-007-B       |    Link Login Activity with user database |
| _**LS-027**_   |    _**Russian language support**_         |

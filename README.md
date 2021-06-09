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

----

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
| _**LS-002**_   |    _**Passwords overview**_               |
| _**LS-003**_   |    _**Create vault entry**_               |
| _**LS-004**_   |    _**Edit password**_                    |
| _**LS-005**_   |    _**Remove password**_                  |
| _**LS-020**_   |    _**Vault search function**_            |
| _**LS-021**_   |    _**Vault Overview sorting**_           |
| _**LS-031**_   |    _**Application icon**_                 |

----
## Pairing matrix

| Pair                  | Johannes Becker   	  | Tobias Pichler              | Lukas Preitler       | Christina Sophie Knes | Bernhard Paul Lippe   | Thomas Reith |
| :---                  |    :----:            |    :----:                   |    :----:            |    :----:             |    :----:             |    :----:    |   
| Johannes Becker       | X                    | 04:00                       | 08:00                | 07:30                 | 02:00                 | 11:00        |
| Tobias Pichler        | 04:00                | X                           | 14:00                | 04:00                 | 06:00                 | 03:30        |
| Lukas Preitler        | 08:00                | 14:00                       | X                    | 03:00                 | 02:00                 | 06:00        |
| Christina Sophie Knes | 07:30                | 04:00                       | 03:00                | X                     | 09:00                 | 10:00        |
| Bernhard Paul Lippe   | 02:00                | 06:00                       | 02:00                | 09:00                 | X                     | 11:30        |
| Thomas Reith          | 11:00                | 03:30                       | 06:00                | 10:00                 | 11:30                 | X            |


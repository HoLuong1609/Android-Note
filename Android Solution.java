- checkPermission
	
	if (checkPermission()) {
            presenter?.getPhoneContacts()
        } else {
            //Register permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_PERMISSION_READ_CONTACTS)
            }
        }
		
	private fun checkPermission() : Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                //Permission don't granted
                if (shouldShowRequestPermissionRationale(
                                Manifest.permission.READ_CONTACTS)) {
                    // Permission isn't granted
                    false
                } else {
                    // Permission don't granted and don't show dialog again.
                    false
                }
            } else
                true
        } else {
            return true
        }
    }

		
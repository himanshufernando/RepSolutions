package emerge.projects.repsolutions.ui.location.mvvm


import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import emerge.projects.repsolutions.R
import android.os.Handler
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import emerge.projects.repsolutions.data.modeldata.*


class LocationModelView(application: Application) : AndroidViewModel(application) {

    var locationRepository: LocationRepo = LocationRepo(application)

    val isLocationListLoading = ObservableField<Boolean>()
    val isNewLocationLoading = ObservableField<Boolean>()

    var app : Application = application


    var editTextLocationName = MutableLiveData<String>()
    var editTextLocationAddress = MutableLiveData<String>()
    var editTextAreaAddress = MutableLiveData<String>()



    var selectedDistrict = DistrictList()
    var selectedTown = TownList()
    var selectedArea = AreaList()
    var selectedLocationType = LocationsTypeList()



    fun getLocationList(): MutableLiveData<Locations> {
        return locationRepository.getLocations(isLocationListLoading)
    }

    fun seLoadingAddingStatus(status : Boolean) {
        isNewLocationLoading.set(status)
    }


    fun getDistrictList() :  MutableLiveData<District>{
        return locationRepository.getDistrictList(isNewLocationLoading)
    }

    fun getTownList() : MutableLiveData<Town>{
        return locationRepository.getTown(isNewLocationLoading,selectedDistrict.districtID)

    }

    fun getAreaList(): MutableLiveData<Area>{
        return locationRepository.getArea(isNewLocationLoading)
    }

    fun getLocationTypeList(): MutableLiveData<LocationsTyps>{
        return locationRepository.getLocationType(isNewLocationLoading)
    }


    fun onDistrictItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedDistrict = parent!!.getItemAtPosition(position) as DistrictList
    }

    fun onTownItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedTown = parent!!.getItemAtPosition(position) as TownList
    }

    fun onAreaItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedArea = parent!!.getItemAtPosition(position) as AreaList
    }

    fun onLocationTypeSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedLocationType = parent!!.getItemAtPosition(position) as LocationsTypeList
    }

    fun searchLocation(sreachText : String,locationList : ArrayList<LocationsList>): MutableLiveData<ArrayList<LocationsList>>{
        return locationRepository.searchLocation(sreachText,locationList)
    }



      fun saveNewLocations(currentLocation: LatLng,locationDuplicateStatus : Boolean): MutableLiveData<Locations> {
           return locationRepository.saveNewLocation(isNewLocationLoading,
               currentLocation,
               editTextLocationName.value.toString(),
               editTextLocationAddress.value.toString(),
               editTextAreaAddress.value.toString(),
               selectedTown.townID,
               selectedDistrict.districtID,
               selectedLocationType.locationsTypeID,
               locationDuplicateStatus)
       }


}
import {Component, OnDestroy, OnInit} from '@angular/core';
import {BehaviorSubject, debounceTime, Subject, takeUntil} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Location} from '../../models/location.model';

@Component({
  selector: 'app-search-section',
  standalone: false,
  templateUrl: './search-section.component.html',
  styleUrl: './search-section.component.css',
})
export class SearchSectionComponent implements OnInit, OnDestroy {
  locations: Location[] = [
    {locationId: 1, locationName: 'Baden-Württemberg'},
    {locationId: 2, locationName: 'Bavaria'},
    {locationId: 3, locationName: 'Berlin'},
    {locationId: 4, locationName: 'Brandenburg'},
    {locationId: 5, locationName: 'Bremen'},
    {locationId: 6, locationName: 'Hamburg'},
    {locationId: 7, locationName: 'Hesse'},
    {locationId: 8, locationName: 'Lower Saxony'},
    {locationId: 9, locationName: 'Mecklenburg-Western Pomerania'},
    {locationId: 10, locationName: 'North Rhine-Westphalia'},
    {locationId: 11, locationName: 'Rhineland-Palatinate'},
    {locationId: 12, locationName: 'Saarland'},
    {locationId: 13, locationName: 'Saxony'},
    {locationId: 14, locationName: 'Saxony-Anhalt'},
    {locationId: 15, locationName: 'Schleswig-Holstein'},
    {locationId: 16, locationName: 'Thuringia'}
  ];

  private searchSubject = new BehaviorSubject<string>('');
  private locationSubject = new BehaviorSubject<string>('');

  // Use Subject<void>() to avoid immediate unsubscription
  private destroy$ = new Subject<void>();

  // private injectedHttp = inject(HttpClient);

  constructor(private http: HttpClient) {
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  ngOnInit() {
    this.searchSubject.pipe(debounceTime(500), takeUntil(this.destroy$))
      .subscribe(keyword => {
        this.fetchResults(keyword);
      });
    this.locationSubject.pipe(debounceTime(500), takeUntil(this.destroy$))
      .subscribe(keyword => {
        this.fetchLocations(keyword);
      });
  }

  fetchLocations(keyword: string) {
    console.log('Searching for', keyword);

    if (!keyword.trim()) {
      // this.locations = [];
      return;
    }

    const apiUrl = `https://api.example.com/locations?search=${keyword}`;
    this.http.get<Location[]>(apiUrl).subscribe({
      next: (response) => {
        // this.locations = response; // ✅ Update locations list
        console.log('Fetched Locations:', response);
      },
      error: (err) => console.error('Error fetching locations:', err)
    });
  }

  fetchResults(keyword: string) {
    console.log('Searching for', keyword);
  }

  search() {
    console.log(this.locationSubject.value + "___" + this.searchSubject.value);
  }

  sampleFunction(): string {
    return 'result';
  }

  onKeywordChange(event: Event) {
    this.searchSubject.next((event.target as HTMLInputElement).value);
  }

  onLocationKeywordChange(event: Event) {
    this.locationSubject.next((event.target as HTMLInputElement).value);
  }

  onLocationSelected(locationName: string, event: Event) {
    const isChecked = (event.target as HTMLInputElement).checked;
    console.log(`Radio button for Location ${locationName} is ${isChecked ? 'checked' : 'unchecked'}`);
  }
}

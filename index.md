## |<urs

Kurs tengah BI dalam JSON API yang realtime berdasarkan website BI (https://www.bi.go.id/id/statistik/informasi-kurs/transaksi-bi/default.aspx)

```markdown
GET http://localhost:8080/latest

{
  "baseCurrency": "IDR",
  "rates": {
    "CHF": 1573861.5,
    "HKD": 185189.5,
    "EUR": 1717885.0,
    "DKK": 230990.5,
    "SAR": 383393.0,
    "CAD": 1167756.5,
    "MYR": 347295.5,
    "USD": 1437800.0,
    "NOK": 168801.5,
    "VND": 62.5,
    "CNY": 223519.5,
    "THB": 45789.5,
    "KRW": 1270.5,
    "SGD": 1074188.5,
    "JPY": 12993.545,
    "GBP": 2009614.5,
    "PGK": 409827.0,
    "LAK": 152.0,
    "SEK": 168736.5,
    "BND": 1074188.5,
    "KWD": 4773770.0,
    "NZD": 1014657.0,
    "PHP": 29688.5,
    "CNH": 223387.5
  },
  "updateDate": "2021-06-18"
}```

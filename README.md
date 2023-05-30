<h1 align="center">ecommerce</h1>
<p align="center">By Tude Prayatna</p>

# **Pengenalan**
## Penggunaan -->
### **Alur Program**
**1) GET**
<br>
Method atau fungsi GET digunakan untuk menampilkan data yang diinginkan. Berikut merupakan pengaplikasian 
GET pada project ecommerce.
<br>
<br>
**GET pada users**
<br>
Terdapat beberapa method GET yang digunakan untuk
menampilkan data users pada project ini, diantaranya adalah
menampilkan data semua user, menampilkan data user dengan
id yang dipilih, data produk atau order yang dimiliki user, 
dan tipe (_Buyer_ atau _Seller_) dari user. Berikut 
merupakan cara untuk melihat semua data user.
```
localhost:8068/users
```
Kode diatas merupakan kode untuk menginisialisasi
atau proses untuk menampilkan semua data user. Berikut merupakan
hasil dari sintaks tersebut.
``` 
[
    {
        "last_name": "Prayatna",
        "phone_number": "0871234567",
        "id": 1,
        "type": "Buyer",
        "first_name": "Tude",
        "email": "pray@gmail.com"
    },
    {
        "last_name": "Danarsa",
        "phone_number": "0812345678",
        "id": 2,
        "type": "Seller",
        "first_name": "Devta",
        "email": "dev@gmail.com"
    },
    {
        "last_name": "Yurista",
        "phone_number": "083456789",
        "id": 3,
        "type": "Seller",
        "first_name": "Liangga",
        "email": "luris@gmail.com"
    },
    {
        "last_name": "Juniawan",
        "phone_number": "085648654",
        "id": 4,
        "type": "Seller",
        "first_name": "Indah",
        "email": "ijun@gmail.com"
    }
]
```
Kode diatas merupakan data dari semua user dengan sintaks JSON. 
Berikut merupakan untuk menampilkan data user berdasarkan id.
```
localhost:8068/user/1
```
Kode diatas merupakan kode untuk menampilkan data user berdasarkan id.
Dimana pada kode tersebut data user yang ditampilkan adalah 
data user dengan id 1. Berikut merupakan hasilnya.
```
[
    {
        "last_name": "Prayatna",
        "phone_number": "0871234567",
        "id": 1,
        "type": "Buyer",
        "first_name": "Tude",
        "email": "pray@gmail.com"
    }
]
```
Kode diatas merupakan data user dengan id 1. Berikut merupakan 
produk yang dimiliki user.
```
localhost:8068/user/2/product
```
Kode diatas merupakan kode untuk menampilkan data produk yang dimiliki user.
Dimana pada kode tersebut data produk yang ditampilkan adalah
data user dengan id 2. Berikut merupakan hasilnya.
```
[
    {
        "seller": "2",
        "price": "5000",
        "description": "LV Vintage Jacket",
        "id": 1,
        "title": "Shirt",
        "stock": "6"
    }
]
```
Kode diatas merupakan data produk yang dimiliki user dengan 
id 2. Berikut merupakan cara melihat tipe (_Buyer_ atau _Seller_) 
dari user.
```
localhost:8068/users?type='Seller'
```
Kode diatas merupakan kode untuk menampilkan data tipe dari user.
Dimana pada kode tersebut data tipe dari user yang ditampilkan adalah
tipe user yang ber-tipe 'Seller'. Berikut merupakan hasilnya.
```
[
    {
        "last_name": "Danarsa",
        "phone_number": "0812345678",
        "id": 2,
        "type": "Seller",
        "first_name": "Devta",
        "email": "dev@gmail.com"
    },
    {
        "last_name": "Yurista",
        "phone_number": "083456789",
        "id": 3,
        "type": "Seller",
        "first_name": "Liangga",
        "email": "luris@gmail.com"
    },
    {
        "last_name": "Juniawan",
        "phone_number": "085648654",
        "id": 4,
        "type": "Seller",
        "first_name": "Indah",
        "email": "ijun@gmail.com"
    }
]
```
Kode diatas merupakan data user dengan yang ber-tipe 'Seller'.
Berikut merupakan cara melihat data user dengan _field_ 
dan _condition_ tertentu.
```
localhost:8068/users?field=id&cond=equal&val=1
```
Kode diatas merupakan kode untuk menampilkan data tipe dari user.
Dimana pada kode tersebut data tipe dari user dengan _field_ tertentu ditampilkan adalah
tipe user dengan id 1. Berikut merupakan hasilnya.
```
[
    {
        "last_name": "Prayatna",
        "phone_number": "0871234567",
        "id": 1,
        "type": "Buyer",
        "first_name": "Tude",
        "email": "pray@gmail.com"
    }
]
```
Kode diatas merupakan kode untuk melihat data user dengan
kondisi tertentu. Dimana pada kode tersebut data yang 
ditampilkan adalah user dengan id 1. Berikut merupakan
cara melihat data order yang dimiliki user.
```
localhost:8068/user/1/order
```
Kode diatas merupakan kode untuk menampilkan data order dari user.
Dimana pada kode tersebut data order dari user dengan id 1. Berikut merupakan hasilnya.
```
[
    {
        "note": 2,
        "total": 5000,
        "discount": 0,
        "id": 1,
        "is_paid": 1,
        "buyer": "1"
    }
]
```
Kode diatas merupakan kode untuk menampilkan data order dari
user. Berikut merupakan cara untuk melihat data user bertipe Buyer.
```
localhost:8068/users?type='Buyer'
```
Data diatas merupakan data untuk melihat data user yang bertipe Buyer.
Berikut merupakan hasilnya.
```
[
    {
        "last_name": "Prayatna",
        "phone_number": "0871234567",
        "id": 1,
        "type": "Buyer",
        "first_name": "Tude",
        "email": "pray@gmail.com"
    }
]
```
Sedangkan, berikut merupakan cara untuk melihat data
dari user yang bertipe Seller.
```
localhost:8068/users?type='Seller'
```
Data diatas merupakan data dari user yang bertipe Seller. Berikut merupakan hasil dari data diatas.
```
[
    {
        "last_name": "Danarsa",
        "phone_number": "0812345678",
        "id": 2,
        "type": "Seller",
        "first_name": "Devta",
        "email": "dev@gmail.com"
    },
    {
        "last_name": "Yurista",
        "phone_number": "083456789",
        "id": 3,
        "type": "Seller",
        "first_name": "Liangga",
        "email": "luris@gmail.com"
    },
    {
        "last_name": "Juniawan",
        "phone_number": "085648654",
        "id": 4,
        "type": "Seller",
        "first_name": "Indah",
        "email": "ijun@gmail.com"
    }
]
```
**GET pada products**
<br>
Berikut merupakan cara melihat semua data pada products.
```
localhost:8068/products
```
Berikut merupakan hasil dari data diatas.
```
[
    {
        "seller": "2",
        "price": "5000",
        "description": "LV Vintage Jacket",
        "id": 1,
        "title": "Shirt",
        "stock": "6"
    },
    {
        "seller": "3",
        "price": "",
        "description": "high quality",
        "id": 2,
        "title": "Bordebor's Exclusive Jacket",
        "stock": "6"
    },
    {
        "seller": "3",
        "price": "",
        "description": "bad quality",
        "id": 3,
        "title": "Supreme Exclusive Jacket",
        "stock": "10"
    }
]
```
Berikut merupakan cara melihat data products 
berdasarkan id.
```
localhost:8068/product/1
```
Berikut merupakan hasil dari data diatas.
```
[
    {
        "seller": "2",
        "price": "5000",
        "description": "LV Vintage Jacket",
        "id": 1,
        "title": "Shirt",
        "stock": "6"
    }
]
```
Data diatas merupakan data product dengan id 1. Dimana 
pada data tersebut produk berasal dari seller yang memiliki
id 2.
<br>
<br>
**2) POST**
<br>
**POST pada users**
<br>
Berikut merupakan cara untuk melakukan POST atau menmbuat 
data baru pada users.
```
localhost:8068/user
```
Setelah memasukan data tersebut, pilih metode POST 
pada PostMan. Lalu, masukan data dengan sintaks JSON.
setelah berhasil maka akan ada pesan "1 row has been inserted" 
jika hanya memasukkan 1 data.
<br>
**POST pada products**
Berikut merupakan cara untuk melakukan POST pada products.
```
localhost:8068/products
```
Lalu masukan data yang diinginkan
```
{
        "seller": "3",
        "price": "",
        "description": "bad quality",
        "id": 3,
        "title": "Supreme Exclusive Jacket",
        "stock": "10"
    }
```
Setelah memasukan data tersebut, pilih metode POST
pada PostMan. Lalu, masukan data dengan sintaks JSON.
setelah berhasil maka akan ada pesan "1 row has been inserted"
jika hanya memasukkan 1 data.
<br>
**POST pada orders**
<br>
Berikut merupakan cara untuk melakukan POST pada orders.
```
localhost:8068/order
```
Lalu masukan data yang diinginkan
```
 {
        "id" : 3,
        "buyer" : 1,
        "note": "1",
        "total": 4000,
        "discount": 10,
        "is_paid": 1
    }
```
Setelah memasukan data tersebut, pilih metode POST
pada PostMan. Lalu, masukan data dengan sintaks JSON.
setelah berhasil maka akan ada pesan "1 row has been inserted"
jika hanya memasukkan 1 data.
<br>
<br>
**3) PUT**
<br>
**PUT pada users**
<br>
PUT merupakan cara untuk merubah data. Berikut merupakan 
cara melakukan PUT.
```
localhost:8068/user/1
```
setelah memasukan data tersebut, ubah data yang diinginkan. Ketika sudah,
maka akan muncul pesan "1 row has been updated" jika data 
yang diubah hanya 1. Berikut merupakan contoh untuk 
melakukan PUT menggunakan sintaks JSON, dimana pada tabel 
users mengubah nama belakang menjadi "Ganteng".
```
{
        "id" : 1,
        "first_name" : "Tude",
        "last_name": "Ganteng",
        "email": "pray@gmail.com",
        "phone_number": 087785533382,
        "type": "Buyer"
    }
```

**PUT pada products**
<br>
PUT merupakan cara untuk merubah data. Berikut merupakan
cara melakukan PUT.
```
localhost:8068/product/2
```
setelah memasukan data tersebut, ubah data yang diinginkan. Ketika sudah,
maka akan muncul pesan "1 row has been updated" jika data
yang diubah hanya 1.
<br>
**PUT pada orders**
PUT merupakan cara untuk merubah data. Berikut merupakan
cara melakukan PUT.
```
localhost:8068/order/1
```
setelah memasukan data tersebut, ubah data yang diinginkan. Ketika sudah,
maka akan muncul pesan "1 row has been updated" jika data
yang diubah hanya 1.
<br>
<br>
**4) DELETE**
DELETE merupakan sintaks yang digunakan ketika ingin 
menghapus suatu data. Berikut merupakan cara menghapus data 
```
localhost:8068/order/3
```
Setelah melakukan atau mengisi data tersebut, pilih metode 
DELETE pada PostMan. Ketika data berhasil, maka 
data order dengan id 3 akan terhapus. Akan ada pesan "
Product with ID 3 has been deleted". Cara untuk menghapus 
data lainnya memiliki sintaks yang sama.

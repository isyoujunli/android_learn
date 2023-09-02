package com.example.chapter07_client;

import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter07_client.entity.Contact;

import java.util.ArrayList;

public class ContactAddActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_contact_name;
    private EditText et_contact_phone;
    private EditText et_contact_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);
        et_contact_name = findViewById(R.id.et_contact_name);
        et_contact_phone = findViewById(R.id.et_contact_phone);
        et_contact_email = findViewById(R.id.et_contact_email);
        findViewById(R.id.btn_add_contact).setOnClickListener(this);
        findViewById(R.id.btn_read_contact).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_add_contact:
                // 创建一个联系人对象
                Contact contact = new Contact();
                contact.name = et_contact_name.getText().toString().trim();
                contact.phone = et_contact_phone.getText().toString().trim();
                contact.email = et_contact_email.getText().toString().trim();


                // 方式一，使用ContentResolver多次写入，每次一个字段
                //addContacts(getContentResolver(), contact);
                // 方式二，批处理方式
                // 每一次操作都是一个 ContentProviderOperation，构建一个操作集合，然后一次性执行
                // 好处是，要么全部成功，要么全部失败，保证了事务的一致性
                addFullContacts(getContentResolver(), contact);
                break;
            case R.id.btn_read_contact:
                readPhoneContacts(getContentResolver());
                break;
        }
    }

    // 查询通讯录信息
    @SuppressLint("Range")
    private void readPhoneContacts(ContentResolver resolver) {
        // 先查询 raw_contacts 表，在根据 raw_contacts_id 去查询 data 表
        Cursor cursor = resolver.query(ContactsContract.RawContacts.CONTENT_URI, new String[]{ContactsContract.RawContacts._ID}, null, null, null, null);
        while (cursor.moveToNext()) {
            int rawContactId = cursor.getInt(0);
            Uri uri = Uri.parse("content://com.android.contacts/contacts/" + rawContactId + "/data");
            Cursor dataCursor = resolver.query(uri, new String[]{ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.Contacts.Data.DATA1, ContactsContract.Contacts.Data.DATA2},
                    null, null, null);
            Contact contact = new Contact();
            while (dataCursor.moveToNext()) {
                String data1 = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Contacts.Data.DATA1));
                String mimeType = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Contacts.Data.MIMETYPE));
                switch (mimeType) {
                    //是姓名
                    case ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
                        contact.name = data1;
                        break;

                    //邮箱
                    case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                        contact.email = data1;
                        break;

                    //手机
                    case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                        contact.phone = data1;
                        break;
                }
            }

            dataCursor.close();

            // RawContacts 表中出现的 _id，不一定在 Data 表中都会有对应记录
            if (contact.name != null) {
                Log.d("ning", contact.toString());
            }
        }
        cursor.close();

    }


    // 往手机通讯录一次性添加一个联系人信息（包括主记录、姓名、电话号码、电子邮箱）
    private void addFullContacts(ContentResolver resolver, Contact contact) {
        // 创建一个插入联系人主记录的内容操作器
        ContentProviderOperation op_main = ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI).withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build();
        // 创建一个插入联系人姓名记录的内容操作器
        ContentProviderOperation op_name = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                // 将第0个操作的id，即 raw_contacts 的 id 作为 data 表中的 raw_contact_id
                .withValueBackReference(ContactsContract.Contacts.Data.RAW_CONTACT_ID, 0).withValue(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE).withValue(ContactsContract.Contacts.Data.DATA2, contact.name).build();

        // 创建一个插入联系人电话号码记录的内容操作器
        ContentProviderOperation op_phone = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                // 将第0个操作的id，即 raw_contacts 的 id 作为 data 表中的 raw_contact_id
                .withValueBackReference(ContactsContract.Contacts.Data.RAW_CONTACT_ID, 0).withValue(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE).withValue(ContactsContract.Contacts.Data.DATA1, contact.phone).withValue(ContactsContract.Contacts.Data.DATA2, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build();

        // 创建一个插入联系人电子邮箱记录的内容操作器
        ContentProviderOperation op_email = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                // 将第0个操作的id，即 raw_contacts 的 id 作为 data 表中的 raw_contact_id
                .withValueBackReference(ContactsContract.Contacts.Data.RAW_CONTACT_ID, 0).withValue(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE).withValue(ContactsContract.Contacts.Data.DATA1, contact.email).withValue(ContactsContract.Contacts.Data.DATA2, ContactsContract.CommonDataKinds.Email.TYPE_WORK).build();

        // 声明一个内容操作器的列表，并将上面四个操作器添加到该列表中
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        operations.add(op_main);
        operations.add(op_name);
        operations.add(op_phone);
        operations.add(op_email);

        try {
            resolver.applyBatch(ContactsContract.AUTHORITY, operations);
        } catch (OperationApplicationException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }


    // 往手机通讯录添加一个联系人信息（包括姓名、电话号码、电子邮箱）
    private void addContacts(ContentResolver resolver, Contact contact) {
        ContentValues values = new ContentValues();
        // 往 raw_contacts 添加联系人记录，并获取添加后的联系人编号
        Uri uri = resolver.insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(uri);

        ContentValues name = new ContentValues();
        // 关联联系人编号
        name.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
        // “姓名”的数据类型
        name.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        // 联系人的姓名
        name.put(ContactsContract.Contacts.Data.DATA2, contact.name);
        // 往提供器添加联系人的姓名记录
        resolver.insert(ContactsContract.Data.CONTENT_URI, name);

        ContentValues phone = new ContentValues();
        // 关联联系人编号
        phone.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
        // “电话号码”的数据类型
        phone.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        // 联系人的电话号码
        phone.put(ContactsContract.Contacts.Data.DATA1, contact.phone);
        // 联系类型。1表示家庭，2表示工作
        phone.put(ContactsContract.Contacts.Data.DATA2, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        // 往提供器添加联系人的姓名记录
        resolver.insert(ContactsContract.Data.CONTENT_URI, phone);


        ContentValues email = new ContentValues();
        // 关联联系人编号
        email.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
        //  “电子邮箱”的数据类型
        email.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        // 联系人的电子邮箱
        email.put(ContactsContract.Contacts.Data.DATA1, contact.email);
        // 联系类型。1表示家庭，2表示工作
        email.put(ContactsContract.Contacts.Data.DATA2, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
        // 往提供器添加联系人的姓名记录
        resolver.insert(ContactsContract.Data.CONTENT_URI, email);


    }
}
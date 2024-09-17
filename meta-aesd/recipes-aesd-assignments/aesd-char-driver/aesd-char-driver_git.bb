# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesd-char-dev-start-stop.sh"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-julian-muellner.git;protocol=ssh;branch=master"

PV = "1.0+git${SRCPV}"
SRCREV = "9e7d8d7569c97d5e4a4a362f9054ffedcb6d895b"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module
FILES:${PN} += "${sysconfdir}/init.d/aesd-char-dev-start-stop.sh"
do_install () {
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${S}/aesd-char-dev-start-stop.sh ${D}${sysconfdir}/init.d/

        install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
        install -m 0755 ${S}/aesdchar.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
}

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"
